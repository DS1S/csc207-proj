package utility;

import java.util.List;
import java.util.Map;

public class ParallelSorter {

    public void parallelSortListMap(List<Integer> arr, List<Map<String, Object>> arr1) {
        int n = arr.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (arr.get(j) < arr.get(j+1))
                {
                    int temp = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,temp);
                    Map<String, Object> temp1 = arr1.get(j);
                    arr1.set(j,arr1.get(j+1));
                    arr1.set(j+1,temp1);
                }
            }
        }
    }

    public void parallelSortListString(List<Integer> arr, List<String> arr1) {
        int n = arr.size();
        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (arr.get(j) < arr.get(j+1))
                {
                    int temp = arr.get(j);
                    arr.set(j,arr.get(j+1));
                    arr.set(j+1,temp);
                    String temp1 = arr1.get(j);
                    arr1.set(j,arr1.get(j+1));
                    arr1.set(j+1,temp1);
                }
            }
        }
    }
}
