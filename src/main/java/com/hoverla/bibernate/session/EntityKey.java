package com.hoverla.bibernate.session;

public record EntityKey<T>(Class<T> type, Object id) {
}
