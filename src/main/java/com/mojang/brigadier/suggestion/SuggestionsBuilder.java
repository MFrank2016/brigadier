package com.mojang.brigadier.suggestion;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.StringRange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SuggestionsBuilder {
    private final String input;
    private final int start;
    private final String remaining;
    private final List<Suggestion> result = new ArrayList<>();

    public SuggestionsBuilder(final String input, final int start) {
        this.input = input;
        this.start = start;
        this.remaining = input.substring(start);
    }

    public String getInput() {
        return input;
    }

    public int getStart() {
        return start;
    }

    public String getRemaining() {
        return remaining;
    }

    public Suggestions build() {
        return Suggestions.create(input, result);
    }

    public CompletableFuture<Suggestions> buildFuture() {
        return CompletableFuture.completedFuture(build());
    }

    public SuggestionsBuilder suggest(final String text) {
        if (text.equals(remaining)) {
            return this;
        }
        result.add(new Suggestion(StringRange.between(start, input.length()), text));
        return this;
    }

    public SuggestionsBuilder suggest(final String text, final Message tooltip) {
        if (text.equals(remaining)) {
            return this;
        }
        result.add(new Suggestion(StringRange.between(start, input.length()), text, tooltip));
        return this;
    }

    public SuggestionsBuilder restart() {
        return new SuggestionsBuilder(input, start);
    }
}
