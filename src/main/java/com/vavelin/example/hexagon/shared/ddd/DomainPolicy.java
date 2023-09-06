package com.vavelin.example.hexagon.shared.ddd;

import java.util.function.Function;

public interface DomainPolicy<T, R> extends Function<T, R> {

}
