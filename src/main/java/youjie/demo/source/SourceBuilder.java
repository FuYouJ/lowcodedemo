package youjie.demo.source;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/21 23:07
 */

public interface SourceBuilder<T extends ISource> {
    T build();
}
