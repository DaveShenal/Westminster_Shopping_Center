package oop.java;

import java.time.LocalDateTime;

// In Java, starting from Java 16, we can use the record
// keyword to create a simple, immutable data carrier class.
// When you declare a record, the compiler automatically
// generates the necessary constructor, accessors (getters),
// equals(), hashCode(), and toString() methods.

public record Purchase(double amount, LocalDateTime date) {
}
