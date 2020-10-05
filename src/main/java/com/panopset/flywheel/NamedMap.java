package com.panopset.flywheel;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;

/**
 * Map wrapper with a map name.
 *
 * @author Karl Dinwiddie
 *
 * @param <K>
 *          Key type.
 * @param <V>
 *          Value type.
 */
public final class NamedMap<K, V> {

  /**
   * Report unused keys. Numeric keys are left out because they are generally
   * reserved for lists.
   * 
   * @param file
   *          File to write report to.
   */
  public static void reportUnusedKeys(final String file) throws IOException {
    KEY_REPORTER.reportUnusedKeys(file);
  }

  /**
   * Key reporter.
   */
  private static final KeyReporter KEY_REPORTER = new KeyReporter();

  /**
   * Name.
   */
  private final String name;

  /**
   * Map.
   */
  private final Map<K, V> map;

  /**
   * @return Map, warning could be null.
   */
  public Map<K, V> getMap() {
    return map;
  }

  /**
   * Constructor.
   * 
   * @param newName
   *          Name.
   */
  public NamedMap(final String newName) {
    this(newName, Collections.synchronizedMap(new TreeMap<K, V>()));
  }

  /**
   * Constructor.
   * 
   * @param newName
   *          Name.
   * @param newMap
   *          Map.
   */
  public NamedMap(final String newName, final Map<K, V> newMap) {
    name = newName;
    map = newMap;
  }

  /**
   * Put key value pair.
   * 
   * @param key
   *          Key.
   * @param value
   *          Value.
   */
  public void put(final K key, final V value) {
    KEY_REPORTER.reportDefinedKey(name, key.toString());
    map.put(key, value);
  }

  /**
   * Get value for a key.
   * 
   * @param key
   *          Key.
   * @return Value associated with key.
   */
  public V get(final K key) {
    KEY_REPORTER.reportUsedKey(name, key.toString());
    return map.get(key);
  }

  /**
   *
   * @return Key reporter.
   */
  public KeyReporter getKeyReporter() {
    return KEY_REPORTER;
  }

  /**
   * Key Reporter is used to help see any un-used variables quickly.
   */
  public static final class KeyReporter {

    /**
     * Get key report.
     * 
     * @param source
     *          Source to get report on.
     * @return Key report.
     */
    KeyReport getKeyReport(final String source) {
      KeyReport rtn = keyReports.get(source);
      if (rtn == null) {
        rtn = new KeyReport();
        keyReports.put(source, rtn);
      }
      return rtn;
    }

    /**
     * Report used key.
     * 
     * @param source
     *          Source where key is used.
     * @param key
     *          Key.
     */
    void reportUsedKey(final String source, final String key) {
      KeyReport keyReport = getKeyReport(source);
      if (!keyReport.getUsedKeys().contains(key)) {
        keyReport.getUsedKeys().add(key);
      }
    }

    /**
     * Report defined key.
     * 
     * @param source
     *          Source where key is defined.
     * @param key
     *          Key.
     */
    void reportDefinedKey(final String source, final String key) {
      KeyReport keyReport = getKeyReport(source);
      if (!keyReport.getDefinedKeys().contains(key)) {
        keyReport.getDefinedKeys().add(key);
      }
    }

    /**
     * Maximum numerics.
     */
    static final int MAXIMUM_NUMERICS = 100;

    /**
     * Report unused keys.
     * 
     * @param file
     *          File.
     */
    void reportUnusedKeys(final String file) throws IOException {
      StringWriter sw = new StringWriter();
      for (String source : keyReports.keySet()) {
        StringWriter hdr = new StringWriter();
        hdr.append("*********" + source + ":");
        hdr.append(Stringop.getEol());
        KeyReport keyReport = getKeyReport(source);
        for (String s : keyReport.getUsedKeys()) {
          keyReport.getDefinedKeys().remove(s);
        }
        for (int i = 0; i < MAXIMUM_NUMERICS; i++) {
          keyReport.getDefinedKeys().remove("" + i);
        }
        keyReport.getDefinedKeys().remove(ReservedWords.FILE);
        keyReport.getDefinedKeys().remove(ReservedWords.DEPTH_CHARGE);
        keyReport.getDefinedKeys().remove(ReservedWords.SCRIPT);
        keyReport.getDefinedKeys().remove(ReservedWords.SPLITS);
        keyReport.getDefinedKeys().remove(ReservedWords.TARGET);
        keyReport.getDefinedKeys().remove(ReservedWords.TEMPLATE);
        keyReport.getDefinedKeys().remove(ReservedWords.TOKENS);
        boolean firstTime = true;
        for (String s : keyReport.getDefinedKeys()) {
          if (firstTime) {
            sw.append(hdr.toString());
            firstTime = false;
          }
          sw.append(s);
          sw.append(Stringop.getEol());
        }
      }
      Fileop.write(sw.toString(), new File(file));
    }

    /**
     * Key reports map.
     */
    private final Map<String, KeyReport> keyReports = Collections
        .synchronizedSortedMap(new TreeMap<String, KeyReport>());

    /**
     * Key report.
     */
    public static class KeyReport {

      /**
       * Used keys.
       */
      private final List<String> usedKeys = new ArrayList<String>();

      /**
       * Defined keys.
       */
      private final Set<String> dfndKeys = Collections
          .synchronizedSortedSet(new TreeSet<String>());

      /**
       * @return Defined keys.
       */
      public Set<String> getDefinedKeys() {
        return dfndKeys;
      }

      /**
       * Get used keys.
       * 
       * @return Used keys.
       */
      public List<String> getUsedKeys() {
        return usedKeys;
      }
    }
  }
}
