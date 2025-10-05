package utilz;

public class Pair<F, S> {
    private F first;
    private  S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
    public void setSecond(S second) {
    	this.second=(S) second;
    }
    public void setFirst(F first) {
    	this.first=first;
    }

    // Optional: You might want to override toString() for better representation
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    // Optional: You can also override equals() and hashCode() for proper comparison
    // Ensure these methods match the logic used to compare the elements in your Pair
}