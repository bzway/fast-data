package ltd.fdsa.core.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * yaml配置内容加载工具类。
 *
 * @author zhumingwu
 * @since 1.0.0
 */
@Slf4j
public abstract class YamlUtils {
    private final static Yaml yaml = new Yaml();

    /**
     * 加载yaml的配置
     *
     * @param payload 源文件内容
     */
    public static Map<String, String> load(String payload) {
        return getSettings((LinkedHashMap<String, Object>) yaml.load(payload), null);
    }

    public static Object loadObj(String payload) {
        return yaml.load(payload);
    }

    /**
     * 加载yaml的配置
     *
     * @param data 源文件内容
     */
    public static String getString(Object data) {
        return yaml.dump(data);
    }

    private static LinkedHashMap<String, String> getSettings(LinkedHashMap<String, Object> list, String prefix) {
        var result = new LinkedHashMap<String, String>();
        for (var item : list.entrySet()) {
            var key = Strings.isNullOrEmpty(prefix) ? item.getKey() : prefix + "." + item.getKey();
            var value = item.getValue();
            if (value instanceof LinkedHashMap) {
                result.putAll(getSettings((LinkedHashMap) value, key));
            } else {
                result.put(key, value.toString());
            }
        }

        return result;

    }
}
