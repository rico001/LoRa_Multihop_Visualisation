package de.htwberlin.lora_multihop_implementation.enums;

public enum EFragments {
    MAP_FRAGMENT(0),
    TERMINAL_FRAGMENT(1);

    private final int fragmentNumber;

    private EFragments(int fragmentNumber) {
        this.fragmentNumber = fragmentNumber;
    }
    public int get() { return fragmentNumber; }

}
