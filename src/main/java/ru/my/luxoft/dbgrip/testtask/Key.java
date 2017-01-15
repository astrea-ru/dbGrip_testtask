package ru.my.luxoft.dbgrip.testtask;

//Key is a simple value class with correct equals() and hashCode() defined
public final class Key {

    private final String key;

    public Key(String key) {
        if (key == null){
            throw new IllegalArgumentException("Key value is null");
        }
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key1 = (Key) o;

        return getKey().equals(key1.getKey());

    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }
}
