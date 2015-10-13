package fr.toto;

import org.mozilla.javascript.ScriptableObject;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ValidMdp {

    public ValidMdp() {
    }

    public static String reverse(String in) {
        return new StringBuffer(in).reverse().toString();
    }

    public static Object[] valid(Object fullObject, Object value, Object params, String property) {

        final Map<String, List<String>> ps =
                (Map<String, List<String>>) params;
        if (ps.containsKey("validations")) {
            List<String> entries = ps.get("validations");
            for (int i = 0; i < entries.size(); i++) {
                String validation = entries.get(i);
                if (validation.equals("ur1-gi-mdp")) {
                    try {
                        Pattern p = Pattern.compile("(^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\W)[0-9a-zA-Z\\\\W][^!#]{7,11}$)");
                        Matcher m = p.matcher((String) value);
                        if (m.matches()) {
                            return new Object[]{};
                        }
                    } catch (final PatternSyntaxException pse) {
                        return new Object[]{new ScriptableObject() {
                            {
                                defineProperty("policyRequirement", "UR1-GI-MAIN", 0);
                            }

                            @Override
                            public String getClassName() {
                                return "PolicyRequirement";
                            }
                        }
                        };
                    }
                }
            }
        }
        return new Object[]{};
    };
}
