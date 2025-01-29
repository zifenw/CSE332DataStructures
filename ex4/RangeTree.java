import java.util.List;

public class RangeTree{
    private OrderedDeletelessDictionary<Double, Range> byStart;
    private OrderedDeletelessDictionary<Double, Range> byEnd;
    private int size;

    public RangeTree(){
        byStart = new AVLTree<>();
        byEnd = new AVLTree<>();
        size = 0;
    }
    
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // Return the Range which starts at the given time
    // The running time is O(log n)
    public Range findByStart(Double start){
        return byStart.find(start);
    }

    // Return the Range which ends at the given time
    // The running time is O(log n)
    public Range findByEnd(Double end){
        return byEnd.find(end);
    }

    // Gives a list of Ranges sorted by start time.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Range> getRanges(){
        return byStart.getValues();
    }

    // Gives a sorted list of start times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getStartTimes(){
        return byStart.getKeys();
    }

    // Gives a sorted list of end times.
    // Useful for testing and debugging.
    // The running time is O(n), so it should not
    // be used for implementing other methods.
    public List<Double> getEndTimes(){
        return byEnd.getKeys();
    }

    // Identifies whether or not the given range conflicts with any
    // ranges that are already in the data structure.
    // If the data structure is empty, then it does not conflict
    // with any ranges, so we should return false.
    // The running time of this method should be O(log n)
    public boolean hasConflict(Range query){
        // TODO
        if (isEmpty()) {
            return false; // No ranges to conflict with
        }
        Double prevEnd = byEnd.findPrevKey(query.end);
        Double prevStart = byStart.findPrevKey(query.end);
        Double nextStart = byStart.findNextKey(query.start);
        Double nextEnd = byEnd.findNextKey(query.start);  
        
        // there are some range before
        if (prevStart != null) {
            // conflict whole period
            if (prevEnd == null || prevStart > prevEnd) {
                return true;
            }
            // conflict for some part of the schedule 
            if (prevEnd > query.start) {
                return true;
            }
        }
        //there are some range after
        if (nextEnd != null) {
            // conflict whole period
            if (nextStart == null || nextEnd < nextStart) {
                return true;
            }
            // conflict for some part of the schedule
            if (nextStart < query.end) {
                return true;
            }

        }

        return false; 
    }
    // Inserts the given range into the data structure if it has no conflict.
    // Does not modify the data structure if it does have a conflict.
    // Return value indicates whether or not the item was successfully
    // added to the data structure.
    // Running time should be O(log n)
    public boolean insert(Range query){
        // TODO
        if (hasConflict(query)) {
            return false; // Cannot insert if there's a conflict
        }
        // Insert the range into both trees
        byStart.insert(query.start, query); // Insert based on the start time
        byEnd.insert(query.end, query); // Insert based on the end time
        size++; // Increment the size of the RangeTree
        return true; // Successfully inserted
    }
}
