package com.framgia.attendance.util.web;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Web出力時のテキストの編集用Util
 */
public class WebStringUtil {

    public static final String longBreak = "<br />";
    public static final String shortBreak = "<br>";

    /**
     * &lt;br /&gt;が検査対象文字列内にないことを確認する。
     * 
     * @param value
     *            検証対象文字列
     * @return 検証対象文字列にBRがない場合はtrue そうでない場合はfalse
     */
    public static boolean notFindBR(String value) {
        return value.indexOf(longBreak) == -1;
    }

    /**
     * 対象文字列の指定文字の後に&gt;br /&lt;を挿入して返却する
     * 
     * @param value
     *            対象文字列
     * @param c
     *            指定文字
     * @return 編集後の文字列
     */
    public static String lineBreak(String value, String c) {
        int idx;
        if ((idx = value.indexOf(c)) >= 0) {
            value =
                    value.substring(0, idx + c.length()) + longBreak
                            + value.substring(idx + c.length());
        }
        return value;
    }

    /**
     * 対象文字列の指定文字の前に&ltbr /;&gt;を挿入して返却する
     * 
     * @param value
     *            対象文字列
     * @param c
     *            指定文字
     * @return 編集後の文字列
     */
    public static String beforLineBreak(String value, String c) {
        int idx;
        if ((idx = value.indexOf(c) - c.length()) >= -c.length()) {
            value =
                    value.substring(0, idx + c.length()) + longBreak
                            + value.substring(idx + c.length());
        }
        return value;
    }

    /**
     * 文字列中の&ltbr /;&gt;を削除する
     * 
     * @param value
     *            対象文字列
     * @return &ltbr /;&gt;の削除された文字列
     */
    public static String eraseBR(String value) {
        return value.replace(longBreak, "");
    }

    /**
     * 文字列中の<br />
     * , <br>
     * を置換する
     * 
     * @param string
     *            文字列
     * @param to
     *            置換する語
     * @return 置換された文字列
     */
    public static String replaceBreakTo(final String string, final String to) {
        String replaced = string.replace(longBreak, to);
        return replaced.replace(shortBreak, to);
    }

    /** HTMLタグを検出するパターン */
    private static final Pattern HTML_TAG = Pattern.compile("<.+?>",
            Pattern.DOTALL);

    /**
     * HTMLタグを除去し、文字列をHTMLアンエスケープする
     * 
     * @param value
     *            対象文字列
     * @return タグが除去されアンエスケープされた文字列
     */
    public static String eraceHtmlTags(String value) {
        if (value == null) {
            return null;
        }
        value = HTML_TAG.matcher(value).replaceAll("");
        return StringEscapeUtils.unescapeHtml(value);
    }

    /**
     * 対象idへの、Windowを閉じるjavascript付きURLで返す
     * 
     * @param id
     *            企業ID
     * @return js jsURL
     */

    public static String getJsCompanyLink(String corpId) {
        final String url =
                "/company/companyinformation/cid/" + corpId + "/isClosed/true";
        return "window.opener.location.href='" + url + "'; window.close();";
    }

    public static String getJsIndustryLink(String idstId) {
        final String url =
                "/industry/industryinformation/iid/" + idstId
                        + "/isClosed/true";
        return "window.opener.location.href='" + url + "'; window.close();";
    }
}
