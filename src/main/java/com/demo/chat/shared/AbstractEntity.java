package com.demo.chat.shared;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<T, E> implements Serializable {

    private static final long serialVersionUID = 1;

    protected abstract E getId();

    @Override
    public int hashCode() {
        return 31 + ((getId() == null) ? 0 : getId().hashCode());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractEntity<T, E> other = (AbstractEntity<T, E>) obj;
        if (getId() == null) {
            return other.getId() == null;
        } else return getId().equals(other.getId());
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s]", getClass().getSimpleName(), getId());
    }

}
