package youjie.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Author} FuYouJ
 * {@code Date} 2023/12/24 16:52
 */

public class Solution {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(candidates, 0, new ArrayList<>(), target, res);
        return res;
    }

    public static void dfs(int[] candidates, int index, List<Integer> temp, int target, List<List<Integer>> res) {
//        if (index > candidates.length - 1 || sum > target) {
//            return;
//        }
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }
//        if (sum == target) {
//            res.add(new ArrayList<>(temp));
//        }
        for (int i = index; i < candidates.length; i++) {
//            sum += candidates[i];
            temp.add(candidates[i]);
            dfs(candidates, i, temp, target - candidates[i], res);
//            sum -= candidates[i];
            temp.remove(temp.size() - 1);
        }
    }

}
