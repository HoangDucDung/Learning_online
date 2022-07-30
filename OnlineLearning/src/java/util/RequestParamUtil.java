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

    public static int parseInt(HttpServletRequest req, String paramName) {
        return parseInt(req, paramName, 0);
    }

    public static int parseInt(HttpServletRequest req, String paramName, int defaultInt) {
        try {
            return Integer.parseInt(req.getParameter(paramName));
        } catch (NumberFormatException ex) {
        }
        return defaultInt;
    }
}
