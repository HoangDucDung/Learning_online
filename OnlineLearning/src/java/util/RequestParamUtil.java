package util;

import javax.servlet.http.HttpServletRequest;

public class RequestParamUtil {

    public static int[] getParamsInt(HttpServletRequest req, String paramName) {
        String[] strs = req.getParameterValues(paramName);
        if (strs == null) {
            return null;
        }
        
        int[] nums = new int[strs.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(strs[i]);
        }

        return nums;
    }
}
