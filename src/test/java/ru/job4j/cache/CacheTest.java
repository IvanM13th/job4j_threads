package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CacheTest {

    @Test
    public void whenAddThenCacheContains() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        assertThat(cache.getMemory()).containsValue(base);
    }

    @Test
    public void whenAddTheDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.getMemory()).isEmpty();

    }

    @Test
    public void whenUpdateAndDifferentVersions() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        assertThatThrownBy(
                () -> cache.update(new Base(1, 2)))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenUpdateAndSameVersionsThenNewVersionAndNewName() {
        Cache cache = new Cache();
        Base firstItem = new Base(1, 1);
        firstItem.setName("FirstItem");
        cache.add(firstItem);
        Base temp = cache.getMemory().get(firstItem.getId());
        temp.setName("SecondName");
        assertThat(cache.update(temp)).isTrue();
        assertThat(cache.getMemory().get(firstItem.getId()).getVersion()).isEqualTo(2);
        assertThat(cache.getMemory().get(firstItem.getId()).getName()).isEqualTo(temp.getName());
    }

}
