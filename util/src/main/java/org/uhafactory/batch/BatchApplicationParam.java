package org.uhafactory.batch;

import static org.apache.commons.cli.Option.builder;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.cli.*;
import org.apache.commons.lang.ArrayUtils;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;

/**
 */
@Getter
@Setter
public class BatchApplicationParam {
	public static final String CONTEXT_JAVA_CLASS_NAME = "j";
	public static final String JOB_NAME = "n";
	public static final String JOB_PARAMETERS = "P";
	public static final String HELP = "h";
	public static final String EXECUTE_ONCE_SAME_PARAM = "o";

	private String jobName;

	private String[] contextJavaClasses;
	private String[] jobParameters;
	private boolean executeOnceSameParameter;
	private String[] args;
	private Class<BatchApplication> defaultContextClass;

	public static BatchApplicationParam parse(String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine result = parser.parse(createOptions(), args);

			BatchApplicationParam param = new BatchApplicationParam();
			param.setJobName(result.getOptionValue(JOB_NAME));
			param.setContextJavaClasses(result.getOptionValues(CONTEXT_JAVA_CLASS_NAME));
			param.setJobParameters(result.getOptionValues(JOB_PARAMETERS));
			param.setExecuteOnceSameParameter(result.hasOption(EXECUTE_ONCE_SAME_PARAM));
			param.setArgs(args);
			return param;
		} catch (Exception e) {
			printHelp();
			throw new RuntimeException(e);
		}
	}

	public static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(10000);
		formatter.printHelp("java -jar batch.jar " + BatchApplicationParam.class.getName(), createOptions());
	}

	private static Options createOptions() {
		return new Options().addOption(builder(JOB_NAME)
                                .desc("execute job name(id)")
                                .longOpt("job-name")
                                .hasArg()
                                .argName("batchJobName")
                                .required().build()
                            ).addOption(builder(JOB_PARAMETERS)
                                .argName("key1=value1 key2=value2 ...")
                                .desc("job parameter, if you want use multiple parameters, input -" + JOB_PARAMETERS + " key1=value1 key2=value2")
                                .longOpt("job-param")
                                .hasArgs()
                                .build()
                            ).addOption(builder(HELP)
                                .desc("usage/help")
                                .longOpt("help")
                                .build()
                            ).addOption(builder(EXECUTE_ONCE_SAME_PARAM)
                                .desc("if include this parameter, job is executed only once when the same parameters. ")
                                .longOpt("execute-once")
                                .build()
                            ).addOption(builder(CONTEXT_JAVA_CLASS_NAME)
                                .argName(CONTEXT_JAVA_CLASS_NAME)
                                .desc("context java class full name")
                                .hasArgs()
                                .argName("com.JavaContextConfiguration1 com.JavaContextConfiguration2 ...")
                                .required()
                                .longOpt("context-java")
                                .build()
        );
	}

	Class<?>[] getContextClasses() throws ClassNotFoundException {
		List<Class<?>> classes = Lists.newArrayList();
		if (defaultContextClass != null) {
			classes.add(defaultContextClass);
		}
		for (String contextClass : contextJavaClasses) {
			classes.add(Class.forName(contextClass));
		}
		return classes.toArray(new Class<?>[classes.size()]);
	}

	public void setDefaultContextClass(Class<BatchApplication> defaultContextClass) {
		this.defaultContextClass = defaultContextClass;
	}

	public String[] getJobParameters() {
		if (!executeOnceSameParameter) {
			return (String[])ArrayUtils.add(jobParameters, "currentDate=" + Calendar.getInstance().getTime());
		}
		return jobParameters;
	}
}
