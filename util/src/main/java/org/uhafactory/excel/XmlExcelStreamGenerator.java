package org.uhafactory.excel;


import org.springframework.util.StringUtils;

import java.beans.PropertyEditor;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 */
public class XmlExcelStreamGenerator implements ExcelStreamGenerator {
    private static final String EXCEL_XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<?mso-application progid=\"Excel.Sheet\"?>\n"
            + "<Workbook\n"
            + "   xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\n"
            + "   xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"
            + "   xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"
            + "   xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n"
            + "   xmlns:html=\"http://www.w3.org/TR/REC-html40\">\n"
            + "  <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\"/>\n"
            + "  <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\" />\n"
            + "  <Styles>\n"
            + "    <Style ss:ID=\"Header\">\n"
            + "      <Font ss:Bold=\"1\" />\n"
            + "      <Interior ss:Pattern=\"Solid\" ss:Color=\"#FFFF99\" />\n"
            + "      <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\" />\n"
            + "      <Borders>\n"
            + "      <Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Color=\"#000000\"/>\n"
            + "      <Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Color=\"#000000\"/>\n"
            + "      <Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Color=\"#000000\"/>\n"
            + "      <Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Color=\"#000000\"/>\n"
            + "      </Borders>\n"
            + "    </Style>\n"
            + "  </Styles>\n"
            + "  <Worksheet ss:Name=\"Sheet1\">\n"
            + "    <Table>";

    private static final String EXCEL_XML_FOOTER = "</Table></Worksheet></Workbook>";

    public byte[] getHeader(List<String> columns) {
        return (EXCEL_XML_HEADER + generateHeaderRow(columns)).getBytes(Charset.forName("UTF-8"));
    }

    public byte[] getFooter() {
        return EXCEL_XML_FOOTER.getBytes();
    }

    public String headerRowStart() {
        return "<Row ss:Height=\"30\">";
    }

    public String rowStart() {
        return "<Row>";
    }

    public String rowEnd() {
        return "</Row>\n";
    }

    public String headerCellStart() {
        return "<Cell ss:StyleID=\"Header\"><Data ss:Type=\"String\">";
    }

    public String numberCellStart() {
        return "<Cell><Data ss:Type=\"Number\">";
    }

    public String stringCellStart() {
        return "<Cell><Data ss:Type=\"String\">";
    }

    public String cellEnd() {
        return "</Data></Cell>";
    }

    public String delemeter() {
        return "";
    }

//    public String escape(String cellValue) {
//        return "<![CDATA[" + cellValue + "]]>";
//    }

    public String generateHeaderRow(List<String> columns) {
        StringBuilder builder = new StringBuilder(256);
        builder.append(headerRowStart());

        Iterator<String> iterator = columns.iterator();

        while (iterator.hasNext()) {
            String columnName = iterator.next();
            builder.append(headerCellStart());
            builder.append(columnName);
            builder.append(cellEnd());

            if (iterator.hasNext()) {
                builder.append(delemeter());
            }
        }

        builder.append(rowEnd());
        return builder.toString();
    }

    @Override
    public byte[] generateRow(List<String> values) {
        StringBuilder builder = new StringBuilder(256);

        builder.append(rowStart());
        Iterator<String> iterator = values.iterator();

        while (iterator.hasNext()) {
            String columnValue = iterator.next();
            builder.append(stringCellStart());
            builder.append(StringUtils.isEmpty(columnValue) ? defaultValue : escape(columnValue));

            builder.append(cellEnd());

            if (iterator.hasNext()) {
                builder.append(delemeter());
            }
        }

        builder.append(rowEnd());

        return builder.toString().getBytes(Charset.defaultCharset());
    }

    String escape(String value){
        return value.replace("&", "&#38;") // do ampersand first!!
                .replace("\"", "&#34;")
                .replace("'", "&#39;")
                .replace("<", "&#60;")
                .replace(">", "&#62;");
    }

    private String generateContent(Object value, PropertyEditor editor) {
        String content = null;

        if (editor != null) {
            editor.setValue(value);
            content = editor.getAsText();
        } else {
            content = (value == null) ? null : value.toString();
        }

        if (content == null) {
            return defaultValue;
        } else {
            return content;
        }
    }

    private String defaultValue = "-";
}
