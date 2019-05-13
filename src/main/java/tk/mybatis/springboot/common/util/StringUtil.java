package tk.mybatis.springboot.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by 秦建平 on 2017/11/8.
 */
public class StringUtil {

    private static byte[] allBytes = new byte[256];
    private static char[] byteToChars = new char[256];
    private static Method toPlainStringMethod;


    /**
     * 判断是否为NUll或者空字符串
     *
     * @param toTest
     * @return
     */
    public static boolean isNullOrEmpty(Object toTest) {
        return toTest == null || toTest.toString().length() == 0;
    }

    /**
     * 判断多个字符串是否为null或者空字符----------且&&
     *
     * @param
     * @return
     */
    public static boolean isAllNotBlank(String... objs) {
        Boolean bool = true;
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                if (objs[i] == null || objs[i].length() == 0) {
                    bool = false;
                    return bool;
                }
            }
        }
        return bool;
    }

    /**
     * 判断多个字符串是否为null或者空字符----------或||
     *
     * @param
     * @return
     */
    public static boolean isOrBlank(Object... objs) {
        Boolean bool = false;
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                if (objs[i] == null || objs[i].toString().length() == 0) {
                    bool = true;
                    return bool;
                }
            }
        }
        return bool;
    }

    /**
     * 判断多个字符串是否为null或者空字符----------或||
     *
     * @param
     * @return
     */
    public static boolean isOrBlank(String... objs) {
        Boolean bool = false;
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                if (objs[i] == null || objs[i].length() == 0) {
                    bool = true;
                    return bool;
                }
            }
        }
        return bool;
    }
    /**
     * 判断一个字符串是否与多个字符串都相等----------且&&
     * @param dataFrom
     * @param suffix
     * @return
     */
    /**
     * 判断多个字符串是否为null或者空字符----------且&&
     *
     * @param
     * @return
     */
    public static boolean isAllEquals(String arg, String... objs) {
        Boolean bool = true;
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                if (!objs[i].equalsIgnoreCase(arg)) {
                    bool = false;
                    return bool;
                }
            }
        }
        return bool;
    }

    /**
     * 判断一个字符串是否与多个字符串都相等----------或||
     *
     * @return
     */
    public static boolean isOrEquals(String arg, String... objs) {
        Boolean bool = false;
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                if (objs[i].equalsIgnoreCase(arg)) {
                    bool = true;
                    return bool;
                }
            }
        }
        return bool;
    }

    private static boolean endsWith(byte[] dataFrom, String suffix) {
        for (int i = 1; i <= suffix.length(); ++i) {
            int dfOffset = dataFrom.length - i;
            int suffixOffset = suffix.length() - i;
            if (dataFrom[dfOffset] != suffix.charAt(suffixOffset)) {
                return false;
            }
        }

        return true;
    }

    public static byte[] escapeEasternUnicodeByteStream(byte[] origBytes, String origString, int offset, int length) {
        if (origBytes != null && origBytes.length != 0) {
            int bytesLen = origBytes.length;
            int bufIndex = 0;
            int strIndex = 0;
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream(bytesLen);

            while (true) {
                if (origString.charAt(strIndex) == 92) {
                    bytesOut.write(origBytes[bufIndex++]);
                } else {
                    int loByte = origBytes[bufIndex];
                    if (loByte < 0) {
                        loByte += 256;
                    }

                    bytesOut.write(loByte);
                    int hiByte;
                    if (loByte >= 128) {
                        if (bufIndex < bytesLen - 1) {
                            hiByte = origBytes[bufIndex + 1];
                            if (hiByte < 0) {
                                hiByte += 256;
                            }

                            bytesOut.write(hiByte);
                            ++bufIndex;
                            if (hiByte == 92) {
                                bytesOut.write(hiByte);
                            }
                        }
                    } else if (loByte == 92 && bufIndex < bytesLen - 1) {
                        hiByte = origBytes[bufIndex + 1];
                        if (hiByte < 0) {
                            hiByte += 256;
                        }

                        if (hiByte == 98) {
                            bytesOut.write(92);
                            bytesOut.write(98);
                            ++bufIndex;
                        }
                    }

                    ++bufIndex;
                }

                if (bufIndex >= bytesLen) {
                    return bytesOut.toByteArray();
                }

                ++strIndex;
            }
        } else {
            return origBytes;
        }
    }

    public static char firstNonWsCharUc(String searchIn) {
        return firstNonWsCharUc(searchIn, 0);
    }

    public static char firstNonWsCharUc(String searchIn, int startAt) {
        if (searchIn == null) {
            return '\u0000';
        } else {
            int length = searchIn.length();

            for (int i = startAt; i < length; ++i) {
                char c = searchIn.charAt(i);
                if (!Character.isWhitespace(c)) {
                    return Character.toUpperCase(c);
                }
            }

            return '\u0000';
        }
    }

    public static final String fixDecimalExponent(String dString) {
        int ePos = dString.indexOf("E");
        if (ePos == -1) {
            ePos = dString.indexOf("e");
        }

        if (ePos != -1 && dString.length() > ePos + 1) {
            char maybeMinusChar = dString.charAt(ePos + 1);
            if (maybeMinusChar != 45 && maybeMinusChar != 43) {
                StringBuffer buf = new StringBuffer(dString.length() + 1);
                buf.append(dString.substring(0, ePos + 1));
                buf.append('+');
                buf.append(dString.substring(ePos + 1, dString.length()));
                dString = buf.toString();
            }
        }

        return dString;
    }


    public static long getLong(byte[] buf) throws NumberFormatException {
        return getLong(buf, 0, buf.length);
    }

    public static long getLong(byte[] buf, int offset, int endpos) throws NumberFormatException {
        byte base = 10;

        int s;
        for (s = offset; Character.isWhitespace((char) buf[s]) && s < endpos; ++s) {
            ;
        }

        if (s == endpos) {
            throw new NumberFormatException(new String(buf));
        } else {
            boolean negative = false;
            if ((char) buf[s] == 45) {
                negative = true;
                ++s;
            } else if ((char) buf[s] == 43) {
                ++s;
            }

            int save = s;
            long cutoff = 9223372036854775807L / (long) base;
            long cutlim = (long) ((int) (9223372036854775807L % (long) base));
            if (negative) {
                ++cutlim;
            }

            boolean overflow = false;

            long i;
            for (i = 0L; s < endpos; ++s) {
                char c = (char) buf[s];
                if (Character.isDigit(c)) {
                    c = (char) (c - 48);
                } else {
                    if (!Character.isLetter(c)) {
                        break;
                    }

                    c = (char) (Character.toUpperCase(c) - 65 + 10);
                }

                if (c >= base) {
                    break;
                }

                if (i <= cutoff && (i != cutoff || (long) c <= cutlim)) {
                    i *= (long) base;
                    i += (long) c;
                } else {
                    overflow = true;
                }
            }

            if (s == save) {
                throw new NumberFormatException(new String(buf));
            } else if (overflow) {
                throw new NumberFormatException(new String(buf));
            } else {
                return negative ? -i : i;
            }
        }
    }

    public static short getShort(byte[] buf) throws NumberFormatException {
        byte base = 10;

        int s;
        for (s = 0; Character.isWhitespace((char) buf[s]) && s < buf.length; ++s) {
            ;
        }

        if (s == buf.length) {
            throw new NumberFormatException(new String(buf));
        } else {
            boolean negative = false;
            if ((char) buf[s] == 45) {
                negative = true;
                ++s;
            } else if ((char) buf[s] == 43) {
                ++s;
            }

            int save = s;
            short cutoff = (short) (32767 / base);
            short cutlim = (short) (32767 % base);
            if (negative) {
                ++cutlim;
            }

            boolean overflow = false;

            short i;
            for (i = 0; s < buf.length; ++s) {
                char c = (char) buf[s];
                if (Character.isDigit(c)) {
                    c = (char) (c - 48);
                } else {
                    if (!Character.isLetter(c)) {
                        break;
                    }

                    c = (char) (Character.toUpperCase(c) - 65 + 10);
                }

                if (c >= base) {
                    break;
                }

                if (i <= cutoff && (i != cutoff || c <= cutlim)) {
                    i = (short) (i * base);
                    i = (short) (i + c);
                } else {
                    overflow = true;
                }
            }

            if (s == save) {
                throw new NumberFormatException(new String(buf));
            } else if (overflow) {
                throw new NumberFormatException(new String(buf));
            } else {
                return negative ? (short) (-i) : i;
            }
        }
    }

    public static final int indexOfIgnoreCase(int startingPosition, String searchIn, String searchFor) {
        if (searchIn != null && searchFor != null && startingPosition <= searchIn.length()) {
            int patternLength = searchFor.length();
            int stringLength = searchIn.length();
            int stopSearchingAt = stringLength - patternLength;
            int i = startingPosition;
            if (patternLength == 0) {
                return -1;
            } else {
                char firstCharOfPatternUc = Character.toUpperCase(searchFor.charAt(0));
                char firstCharOfPatternLc = Character.toLowerCase(searchFor.charAt(0));

                while (true) {
                    label41:
                    while (i >= stopSearchingAt || Character.toUpperCase(searchIn.charAt(i)) == firstCharOfPatternUc || Character.toLowerCase(searchIn.charAt(i)) == firstCharOfPatternLc) {
                        if (i > stopSearchingAt) {
                            return -1;
                        }

                        int j = i + 1;
                        int end = j + patternLength - 1;
                        int k = 1;

                        int searchInPos;
                        int searchForPos;
                        do {
                            if (j >= end) {
                                return i;
                            }

                            searchInPos = j++;
                            searchForPos = k++;
                            if (Character.toUpperCase(searchIn.charAt(searchInPos)) != Character.toUpperCase(searchFor.charAt(searchForPos))) {
                                ++i;
                                continue label41;
                            }
                        }
                        while (Character.toLowerCase(searchIn.charAt(searchInPos)) == Character.toLowerCase(searchFor.charAt(searchForPos)));

                        ++i;
                    }

                    ++i;
                }
            }
        } else {
            return -1;
        }
    }

    public static final int indexOfIgnoreCase(String searchIn, String searchFor) {
        return indexOfIgnoreCase(0, searchIn, searchFor);
    }

    public static int indexOfIgnoreCaseRespectMarker(int startAt, String src, String target, String marker, String markerCloses, boolean allowBackslashEscapes) {
        char contextMarker = 0;
        boolean escaped = false;
        int markerTypeFound = 0;
        int srcLength = src.length();
        boolean ind = false;

        for (int i = startAt; i < srcLength; ++i) {
            char c = src.charAt(i);
            if (allowBackslashEscapes && c == 92) {
                escaped = !escaped;
            } else if (c == markerCloses.charAt(markerTypeFound) && !escaped) {
                contextMarker = 0;
            } else {
                int var13;
                if ((var13 = marker.indexOf(c)) != -1 && !escaped && contextMarker == 0) {
                    markerTypeFound = var13;
                    contextMarker = c;
                } else if (c == target.charAt(0) && !escaped && contextMarker == 0 && indexOfIgnoreCase(i, src, target) != -1) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static int indexOfIgnoreCaseRespectQuotes(int startAt, String src, String target, char quoteChar, boolean allowBackslashEscapes) {
        char contextMarker = 0;
        boolean escaped = false;
        int srcLength = src.length();

        for (int i = startAt; i < srcLength; ++i) {
            char c = src.charAt(i);
            if (allowBackslashEscapes && c == 92) {
                escaped = !escaped;
            } else if (c == contextMarker && !escaped) {
                contextMarker = 0;
            } else if (c == quoteChar && !escaped && contextMarker == 0) {
                contextMarker = c;
            } else if ((Character.toUpperCase(c) == Character.toUpperCase(target.charAt(0)) || Character.toLowerCase(c) == Character.toLowerCase(target.charAt(0))) && !escaped && contextMarker == 0 && startsWithIgnoreCase(src, i, target)) {
                return i;
            }
        }

        return -1;
    }

    public static final List split(String stringToSplit, String delimitter, boolean trim) {
        if (stringToSplit == null) {
            return new ArrayList();
        } else if (delimitter == null) {
            throw new IllegalArgumentException();
        } else {
            StringTokenizer tokenizer = new StringTokenizer(stringToSplit, delimitter, false);

            ArrayList splitTokens;
            String token;
            for (splitTokens = new ArrayList(tokenizer.countTokens()); tokenizer.hasMoreTokens(); splitTokens.add(token)) {
                token = tokenizer.nextToken();
                if (trim) {
                    token = token.trim();
                }
            }

            return splitTokens;
        }
    }

    public static final List split(String stringToSplit, String delimiter, String markers, String markerCloses, boolean trim) {
        if (stringToSplit == null) {
            return new ArrayList();
        } else if (delimiter == null) {
            throw new IllegalArgumentException();
        } else {
            boolean delimPos = false;
            int currentPos = 0;

            ArrayList splitTokens;
            String token;
            int delimPos1;
            for (splitTokens = new ArrayList(); (delimPos1 = indexOfIgnoreCaseRespectMarker(currentPos, stringToSplit, delimiter, markers, markerCloses, false)) != -1; currentPos = delimPos1 + 1) {
                token = stringToSplit.substring(currentPos, delimPos1);
                if (trim) {
                    token = token.trim();
                }

                splitTokens.add(token);
            }

            if (currentPos < stringToSplit.length()) {
                token = stringToSplit.substring(currentPos);
                if (trim) {
                    token = token.trim();
                }

                splitTokens.add(token);
            }

            return splitTokens;
        }
    }

    private static boolean startsWith(byte[] dataFrom, String chars) {
        for (int i = 0; i < chars.length(); ++i) {
            if (dataFrom[i] != chars.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean startsWithIgnoreCase(String searchIn, int startAt, String searchFor) {
        return searchIn.regionMatches(true, startAt, searchFor, 0, searchFor.length());
    }

    public static boolean startsWithIgnoreCase(String searchIn, String searchFor) {
        return startsWithIgnoreCase(searchIn, 0, searchFor);
    }

    public static boolean startsWithIgnoreCaseAndNonAlphaNumeric(String searchIn, String searchFor) {
        if (searchIn == null) {
            return searchFor == null;
        } else {
            boolean beginPos = false;
            int inLength = searchIn.length();

            int var5;
            for (var5 = 0; var5 < inLength; ++var5) {
                char c = searchIn.charAt(var5);
                if (Character.isLetterOrDigit(c)) {
                    break;
                }
            }

            return startsWithIgnoreCase(searchIn, var5, searchFor);
        }
    }

    public static boolean startsWithIgnoreCaseAndWs(String searchIn, String searchFor) {
        return startsWithIgnoreCaseAndWs(searchIn, searchFor, 0);
    }

    public static boolean startsWithIgnoreCaseAndWs(String searchIn, String searchFor, int beginPos) {
        if (searchIn == null) {
            return searchFor == null;
        } else {
            for (int inLength = searchIn.length(); beginPos < inLength && Character.isWhitespace(searchIn.charAt(beginPos)); ++beginPos) {
                ;
            }

            return startsWithIgnoreCase(searchIn, beginPos, searchFor);
        }
    }

    public static byte[] stripEnclosure(byte[] source, String prefix, String suffix) {
        if (source.length >= prefix.length() + suffix.length() && startsWith(source, prefix) && endsWith(source, suffix)) {
            int totalToStrip = prefix.length() + suffix.length();
            int enclosedLength = source.length - totalToStrip;
            byte[] enclosed = new byte[enclosedLength];
            int startPos = prefix.length();
            int numToCopy = enclosed.length;
            System.arraycopy(source, startPos, enclosed, 0, numToCopy);
            return enclosed;
        } else {
            return source;
        }
    }

    public static final String toAsciiString(byte[] buffer) {
        return toAsciiString(buffer, 0, buffer.length);
    }

    public static final String toAsciiString(byte[] buffer, int startPos, int length) {
        char[] charArray = new char[length];
        int readpoint = startPos;

        for (int i = 0; i < length; ++i) {
            charArray[i] = (char) buffer[readpoint];
            ++readpoint;
        }

        return new String(charArray);
    }

    public static int wildCompare(String searchIn, String searchForWildcard) {
        if (searchIn != null && searchForWildcard != null) {
            if (searchForWildcard.equals("%")) {
                return 1;
            } else {
                byte result = -1;
                byte wildcardMany = 37;
                byte wildcardOne = 95;
                byte wildcardEscape = 92;
                int searchForPos = 0;
                int searchForEnd = searchForWildcard.length();
                int searchInPos = 0;
                int searchInEnd = searchIn.length();

                label144:
                do {
                    if (searchForPos != searchForEnd) {
                        for (char wildstrChar = searchForWildcard.charAt(searchForPos); searchForWildcard.charAt(searchForPos) != wildcardMany && wildstrChar != wildcardOne; result = 1) {
                            if (searchForWildcard.charAt(searchForPos) == wildcardEscape && searchForPos + 1 != searchForEnd) {
                                ++searchForPos;
                            }

                            if (searchInPos == searchInEnd || Character.toUpperCase(searchForWildcard.charAt(searchForPos++)) != Character.toUpperCase(searchIn.charAt(searchInPos++))) {
                                return 1;
                            }

                            if (searchForPos == searchForEnd) {
                                return searchInPos != searchInEnd ? 1 : 0;
                            }
                        }

                        if (searchForWildcard.charAt(searchForPos) != wildcardOne) {
                            continue;
                        }

                        while (true) {
                            if (searchInPos == searchInEnd) {
                                return result;
                            }

                            ++searchInPos;
                            ++searchForPos;
                            if (searchForPos >= searchForEnd || searchForWildcard.charAt(searchForPos) != wildcardOne) {
                                if (searchForPos != searchForEnd) {
                                    continue label144;
                                }
                                break;
                            }
                        }
                    }

                    return searchInPos != searchInEnd ? 1 : 0;
                } while (searchForWildcard.charAt(searchForPos) != wildcardMany);

                ++searchForPos;

                for (; searchForPos != searchForEnd; ++searchForPos) {
                    if (searchForWildcard.charAt(searchForPos) != wildcardMany) {
                        if (searchForWildcard.charAt(searchForPos) != wildcardOne) {
                            break;
                        }

                        if (searchInPos == searchInEnd) {
                            return -1;
                        }

                        ++searchInPos;
                    }
                }

                if (searchForPos == searchForEnd) {
                    return 0;
                } else if (searchInPos == searchInEnd) {
                    return -1;
                } else {
                    char cmp;
                    if ((cmp = searchForWildcard.charAt(searchForPos)) == wildcardEscape && searchForPos + 1 != searchForEnd) {
                        ++searchForPos;
                        cmp = searchForWildcard.charAt(searchForPos);
                    }

                    ++searchForPos;

                    do {
                        while (searchInPos != searchInEnd && Character.toUpperCase(searchIn.charAt(searchInPos)) != Character.toUpperCase(cmp)) {
                            ++searchInPos;
                        }

                        if (searchInPos++ == searchInEnd) {
                            return -1;
                        }

                        int tmp = wildCompare(searchIn, searchForWildcard);
                        if (tmp <= 0) {
                            return tmp;
                        }
                    } while (searchInPos != searchInEnd && searchForWildcard.charAt(0) != wildcardMany);

                    return -1;
                }
            }
        } else {
            return -1;
        }
    }

    public static int lastIndexOf(byte[] s, char c) {
        if (s == null) {
            return -1;
        } else {
            for (int i = s.length - 1; i >= 0; --i) {
                if (s[i] == c) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static int indexOf(byte[] s, char c) {
        if (s == null) {
            return -1;
        } else {
            int length = s.length;

            for (int i = 0; i < length; ++i) {
                if (s[i] == c) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static String stripComments(String src, String stringOpens, String stringCloses, boolean slashStarComments, boolean slashSlashComments, boolean hashComments, boolean dashDashComments) {
        if (src == null) {
            return null;
        } else {
            StringBuffer buf = new StringBuffer(src.length());
            StringReader sourceReader = new StringReader(src);
            int contextMarker = 0;
            boolean escaped = false;
            int markerTypeFound = -1;
            boolean ind = false;
            boolean currentChar = false;

            int currentChar1;
            try {
                label141:
                while ((currentChar1 = sourceReader.read()) != -1) {
                    if (markerTypeFound != -1 && currentChar1 == stringCloses.charAt(markerTypeFound) && !escaped) {
                        contextMarker = 0;
                        markerTypeFound = -1;
                    } else {
                        int ind1;
                        if ((ind1 = stringOpens.indexOf(currentChar1)) != -1 && !escaped && contextMarker == 0) {
                            markerTypeFound = ind1;
                            contextMarker = currentChar1;
                        }
                    }

                    if (contextMarker == 0 && currentChar1 == 47 && (slashSlashComments || slashStarComments)) {
                        currentChar1 = sourceReader.read();
                        if (currentChar1 == 42 && slashStarComments) {
                            int ioEx = 0;

                            while (true) {
                                if ((currentChar1 = sourceReader.read()) == 47 && ioEx == 42) {
                                    continue label141;
                                }

                                if (currentChar1 == 13) {
                                    currentChar1 = sourceReader.read();
                                    if (currentChar1 == 10) {
                                        currentChar1 = sourceReader.read();
                                    }
                                } else if (currentChar1 == 10) {
                                    currentChar1 = sourceReader.read();
                                }

                                if (currentChar1 < 0) {
                                    continue label141;
                                }

                                ioEx = currentChar1;
                            }
                        }

                        if (currentChar1 == 47 && slashSlashComments) {
                            while ((currentChar1 = sourceReader.read()) != 10 && currentChar1 != 13 && currentChar1 >= 0) {
                                ;
                            }
                        }
                    } else if (contextMarker == 0 && currentChar1 == 35 && hashComments) {
                        while ((currentChar1 = sourceReader.read()) != 10 && currentChar1 != 13 && currentChar1 >= 0) {
                            ;
                        }
                    } else if (contextMarker == 0 && currentChar1 == 45 && dashDashComments) {
                        label156:
                        {
                            currentChar1 = sourceReader.read();
                            if (currentChar1 != -1 && currentChar1 == 45) {
                                while (true) {
                                    if ((currentChar1 = sourceReader.read()) == 10 || currentChar1 == 13 || currentChar1 < 0) {
                                        break label156;
                                    }
                                }
                            }

                            buf.append('-');
                            if (currentChar1 != -1) {
                                buf.append(currentChar1);
                            }
                            continue;
                        }
                    }

                    if (currentChar1 != -1) {
                        buf.append((char) currentChar1);
                    }
                }
            } catch (IOException var15) {
                ;
            }

            return buf.toString();
        }
    }

    public static final boolean isEmptyOrWhitespaceOnly(String str) {
        if (str != null && str.length() != 0) {
            int length = str.length();

            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * @deprecated
     */
    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, char separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return StringUtil.toString(first);
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while (iterator.hasNext()) {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return StringUtil.toString(first);
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while (iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterable<?> iterable, char separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String join(Iterable<?> iterable, String separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if (end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0 ? 0 : increase;
                increase *= max < 0 ? 16 : (max > 64 ? 64 : max);

                StringBuilder buf;
                for (buf = new StringBuilder(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if (max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }


    public static void main(String[] arg) {
        /*List list=new ArrayList();
        list.add("1");
        list.add("2");
        list.add("1");
        list.add("d");
        list.add("ff");
        list.add("g");
        String a=StringUtil.join(list.toArray(),",");
        String b=list.toString().replaceAll("1","f");*/
        boolean b = isOrBlank("sdadgh", "76", null, "gdsayu");

        System.out.println(b);
    }

}
