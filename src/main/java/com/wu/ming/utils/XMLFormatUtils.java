package com.wu.ming.utils;


import com.wu.ming.common.ErrorCode;
import com.wu.ming.exception.BusinessException;

/**
 * 格式化xml字符串
 * @author orange
 */
public class XMLFormatUtils {

    // public static void main(String[] args) {
    //     System.out.println(new XMLFormat("<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>王小为</author><title>深入在线工具</title><publisher>aTool.org组织</publisher></book></books>").format());
    // }

    // private String content; //xml字符串
    // public XMLFormat(String content) {
    //     this.content = content;
    // }

    public static String format(String xmlStr) {
        String header = getHeader(xmlStr);
        return (StringUtils.isEmpty(header) ? "" : header)
                + format(null, xmlStr, 0);
    }

    private static String format(String tag, String content, int depth) {
        String format = "";
        String firstTag = "";
        if (StringUtils.isEmpty(tag)) {
            firstTag = getFirstTag(content);
        } else {
            firstTag = tag;
        }

        String inside = getInsideContent(firstTag, content);
        String outside = getOutsideContent(firstTag, content);

        String insideTag = "";
        try {
            insideTag = getFirstTag(inside);
        } catch (Exception e) {
            insideTag = null;
        }
        if (StringUtils.isEmpty(insideTag)) {
            format = "\r\n" + indent(depth) + "<" + firstTag + ">"
                    + inside + "</" + firstTag.split(" ")[0] + ">";
        } else {
            format = "\r\n" + indent(depth) + "<" + firstTag + ">"
                    + format(insideTag, inside, depth + 1)
                    + indent(depth) + "</" + firstTag.split(" ")[0] + ">";
        }

        String outsideTag = "";
        if (StringUtils.isEmpty(outside)) {
            outsideTag = null;
        } else {
            outsideTag = getFirstTag(outside);
        }
        if (!StringUtils.isEmpty(outsideTag)) {
            format += indent(depth) + format(outsideTag, outside, depth);
        } else if (StringUtils.isEmpty(outside)) {
            format += "\r\n";
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"xml报文格式不正确");
        }
        return format;
    }

    /**
     * 获取xml头部数据，格式：<? …… ?>
     * @return xml头部数据，null表示不存在
     */
    private static String getHeader(String content) {
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == ' ' || c == '\r' || c == '\n'
                    || c == '\t') {
                continue;
            }

            if (c == '<' && content.charAt(i + 1) == '?') {
                String header = "<?";
                for (i = i + 2; i < content.length(); i++) {
                    char end = content.charAt(i);
                    if (end == '?' && content.charAt(i + 1) == '>') {
                        header += "?>";
                        content = content.substring(i + 2);
                        return header;
                    } else {
                        header += end;
                    }
                }
            }

            return null;
        }

        return null;
    }

    /**
     * 获取xml报文的第一个标签
     * @param content xml报文
     * @return 标签名称
     */
    private static String getFirstTag(String content) {
        StringBuilder tag = new StringBuilder();
        int index = 0;

        for (; index < content.length(); index++) {
            char temp = content.charAt(index);
            if (temp == ' ' || temp == '\r' || temp == '\n'
                    || temp == '\t') { //忽略空格回车字符
                continue;
            }

            if (temp != '<') {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "xml报文格式不正确");
            }
            break;
        }

        for (int i = index + 1; i < content.length(); i++) {

            char c = content.charAt(i);
            if (c == '>') {
                return tag.toString();
            }
            tag.append(c);
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "xml报文格式不正确");
    }

    private static String getOutsideContent(String tag, String content) {
        String endTag = "</" + tag.split(" ")[0] + ">";
        int endIndex = content.indexOf(endTag) + endTag.length();

        return content.substring(endIndex);
    }

    private static String getInsideContent(String tag, String content) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag.split(" ")[0] + ">";

        int startIndex = content.indexOf(startTag) + startTag.length();
        int endIndex = content.indexOf(endTag);

        return content.substring(startIndex, endIndex);
    }

    private static String indent(int num) {
        String space = "";
        if (num == 0) {
            return space;
        } else {
            return space + PER_SPACE + indent(num - 1);
        }
    }

    private static final String PER_SPACE = "    "; //缩进字符串
}
