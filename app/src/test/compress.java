import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class compress {

    public static void main(String[] args) {
        File file = new File("F:\\codeNew\\block\\branches\\project\\app\\src\\test\\city.json");
        String s = txt2String(file);
        String ss =minify(s);
        System.out.println(ss);


    }

    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    public static String minify(String jsonString) {
        boolean in_string = false;
        boolean in_multiline_comment = false;
        boolean in_singleline_comment = false;
        char string_opener = 'x'; // unused value, just something that makes compiler happy

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < jsonString.length(); i++) {
            // get next (c) and next-next character (cc)

            char c = jsonString.charAt(i);
            String cc = jsonString.substring(i, Math.min(i + 2, jsonString.length()));

            // big switch is by what mode we're in (in_string etc.)
            if (in_string) {
                if (c == string_opener) {
                    in_string = false;
                    out.append(c);
                } else if (c == '\\') { // no special treatment needed for \\u, it just works like this too
                    out.append(cc);
                    ++i;
                } else
                    out.append(c);
            } else if (in_singleline_comment) {
                if (c == '\r' || c == '\n')
                    in_singleline_comment = false;
            } else if (in_multiline_comment) {
                if (cc.equals("*/")) {
                    in_multiline_comment = false;
                    ++i;
                }
            } else {
                // we're outside of the special modes, so look for mode openers (comment start, string start)
                if (cc.equals("/*")) {
                    in_multiline_comment = true;
                    ++i;
                } else if (cc.equals("//")) {
                    in_singleline_comment = true;
                    ++i;
                } else if (c == '"' || c == '\'') {
                    in_string = true;
                    string_opener = c;
                    out.append(c);
                } else if (!Character.isWhitespace(c))
                    out.append(c);
            }
        }
        return out.toString();
    }

}
