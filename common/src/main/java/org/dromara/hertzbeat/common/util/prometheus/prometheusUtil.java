package org.dromara.hertzbeat.common.util.prometheus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class prometheusUtil {

    private static final int ERROR_FORMAT = -1; //解析过程中出现了未知格式数据，因为无法继续解析或已经到达输入流的末尾

    private static final int NORMAL_END = -2; //输入流正常结束


    private int parseMetricName(InputStream inputStream, Metric.MetricBuilder metricBuilder) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int i;
        i = inputStream.read();
        while(i != -1) {
            if (i == ' ' || i == '{') {
                metricBuilder.metricName(stringBuilder.toString());
                return i;
            }
            stringBuilder.append(i);
            i = inputStream.read();
        }

        return ERROR_FORMAT;
    }

    private int parseLabel(InputStream inputStream, List<Label> labelList) throws IOException {
        Label.LabelBuilder labelBuilder = new Label.LabelBuilder();
        int i;

        StringBuilder labelName = new StringBuilder();
        i = inputStream.read();
        while (i != -1 && i != '=') {
            labelName.append(i);
            i = inputStream.read();
        }
        if (i == -1) {
            return ERROR_FORMAT;
        }
        labelBuilder.name(labelName.toString());


        StringBuilder labelValue = new StringBuilder();
        i = inputStream.read();
        while (i != -1 && i != ',' && i != '}') {
            labelValue.append(i);
            i = inputStream.read();
        }
        if (i == -1) {
            return ERROR_FORMAT;
        }
        labelBuilder.name(labelValue.toString());

        labelList.add(labelBuilder.build());
        return i;
    }

    private int parseLabelList(InputStream inputStream, Metric.MetricBuilder metricBuilder) throws IOException {
        List<Label> labelList = new ArrayList<>();
        int i;

        i = parseLabel(inputStream, labelList);
        while (i == ',') {
            i = parseLabel(inputStream, labelList);
        }
        if (i == -1) {
            return ERROR_FORMAT;
        }

        metricBuilder.labelList(labelList);
        return i;
    }

    private int parseValue(InputStream inputStream, Metric.MetricBuilder metricBuilder) throws IOException {
        int i;

        StringBuilder stringBuilder = new StringBuilder();
        i = inputStream.read();
        while (i != -1 && i != ' ' && i != '\n') {
            stringBuilder.append(i);
            i = inputStream.read();
        }

        String string = stringBuilder.toString();

        switch (string) {
            case "NaN":
                metricBuilder.value(Double.NaN);
                break;
            case "+Inf":
                metricBuilder.value(Double.POSITIVE_INFINITY);
                break;
            case "-Inf":
                metricBuilder.value(Double.NEGATIVE_INFINITY);
                break;
            default:
                try {
                    BigDecimal bigDecimal = new BigDecimal(string);
                    metricBuilder.value(bigDecimal.doubleValue());
                } catch (NumberFormatException e) {
                    return ERROR_FORMAT;
                }
                break;
        }

        if (i == -1) {
            return NORMAL_END;
        }
        else {
            return i; // ' ' or \n'
        }
    }

    private int parseTimestamp(InputStream inputStream, Metric.MetricBuilder metricBuilder) throws IOException {
        int i;

        StringBuilder stringBuilder = new StringBuilder();
        i = inputStream.read();
        while (i != -1 && i != '\n') {
            stringBuilder.append(i);
            i = inputStream.read();
        }

        String string = stringBuilder.toString();
        try {
            metricBuilder.timestamp(Long.parseLong(string));
        } catch (NumberFormatException e) {
            return ERROR_FORMAT;
        }

        if (i == -1) {
            return NORMAL_END;
        }
        else {
            return i; // '\n'
        }
    }

    public int parseMetric(InputStream inputStream, Metric metric) throws IOException {
        int i = -1;
        Metric.MetricBuilder metricBuilder = new Metric.MetricBuilder();

        i = parseMetricName(inputStream, metricBuilder); // RET: -1, '{', ' '
        if (i == ERROR_FORMAT) {
            return i;
        }

        if (i == '{') {
            i = parseLabelList(inputStream, metricBuilder); // RET: -1, '}'
            if (i == ERROR_FORMAT) {
                return i;
            }
        }

        i = parseValue(inputStream, metricBuilder); // RET: -1, -2, '\n', ' '
        if (i != ' ') {
            return i;
        }

        i = parseTimestamp(inputStream, metricBuilder); // RET: -1, -2, '\n'
        return i;

    }
}
