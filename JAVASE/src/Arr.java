import java.util.*;
import java.util.stream.Collectors;

public class Arr {

    public static void main(String[] args) {
        Integer[] nums = {1, 2, 33, 3, 3, 3, 4, 62, 124, 12, 56, 1, 23, 45, 1, 2, 41, 2, 33, 55, 1, 2, 35, 23, 4, 54, 7, 1, 3, 34, 42, 3, 32, 32, 3, 3, 3, 3, 5};
        Integer[] numsCopy = {3, 3, 5, 1, 5, 6, 1, 33, 5, 5, 134, 12, 412, 3, 3, 64, 634, 2134, 1234, 12, 34, 1234, 12, 4};
        oneInt(numsCopy, nums);
        one(nums, numsCopy);
        two(numsCopy);
        twoInt(numsCopy);
        three(twoInt(nums));
        threeInt(twoInt(nums));
        four(nums);
        fourInt(numsCopy);
        fiveInt(nums);
    }

    static void oneInt(Integer[] nums, Integer[] numsCopy) {
        Integer[][] in = new Integer[1][2];
        int inIndex = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            boolean b = true;
            if (inIndex == in.length) {//动态数组
                Integer[][] temp = new Integer[inIndex + 1][2];
                int tempIndex = 0;
                for (int k = 0; k < inIndex; k++) {
                    temp[tempIndex][0] = in[k][0];
                    temp[tempIndex][1] = in[k][1];
                    tempIndex++;
                }
                in = temp;
                inIndex = tempIndex;
            }
            for (int j = 0; j < numsCopy.length; j++) {
                if (nums[i].equals(numsCopy[j])) {//如果等于
                    for (int l = 0; l < in.length; l++) {
                        if (null != in[l][0] && in[l][0].equals(nums[i])) {//找到并且不是null空
                            b = false;
                            int count = in[l][1];
                            in[l][1] = count + 1;
                            if (in[l][1] > max) {
                                max = in[l][1];
                            }
                        }

                    }
                    if (b) {
                        in[inIndex][0] = nums[i];
                        in[inIndex][1] = 1;
                        inIndex++;
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < inIndex; i++) {
            if (in[i][1] == max) {
                System.out.println("共同出现次数最多的数字 = " + in[i][0] + ", 出现次数 = " + in[i][1]);
            }
        }

    }

    static void one(Integer[] nums, Integer[] numsCopy) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0; i < nums.length; i++) {//遍历属猪长度
            for (int j = 0; j < numsCopy.length; j++) {
                if (nums[i].equals(numsCopy[j])) {//如果等于
                    if (map.containsKey(nums[i])) {//加入
                        map.put(nums[i], map.get(nums[i]) + 1);
                        if (map.get(nums[i]) > max) {
                            max = map.get(nums[i]);
                        }
                    } else {
                        map.put(nums[i], 1);
                    }
                    break;
                }
            }
        }
        Set<Integer> integers = map.keySet();
        for (Integer i : integers) {
            if (map.get(i) == max) {
                System.out.println("共同出现次数最多的数字 = " + i + ", 出现次数 = " + map.get(i));
            }
        }

    }

    static Set two(Integer[] nums) {
        System.out.println("去重前 = " + Arrays.asList(nums));
        Set<Integer> integers = new HashSet<>(Arrays.asList(nums));
        System.out.println("去重后 = " + integers);
        return integers;
    }

    static Integer[] twoInt(Integer[] nums) {
        Integer[] temp = new Integer[1];
        int tempIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            boolean b = true;
            if (tempIndex == temp.length) {
                Integer[] newTemp = new Integer[tempIndex + 1];
                int newIndex = 0;
                for (int j = 0; j < tempIndex; j++) {
                    newTemp[newIndex++] = temp[j];
                }
                temp = newTemp;
                tempIndex = newIndex;
            }
            for (int j = 0; j < temp.length; j++) {
                if (null != nums[i] && nums[i].equals(temp[j])) {
                    b = false;
                    break;
                }

            }
            if (b) {
                temp[tempIndex++] = nums[i];
            }
        }
        System.out.println("去重前 = " + Arrays.asList(nums));
        System.out.println("去重后 = " + Arrays.asList(temp));
        return temp;
    }

    static void three(Integer[] nums) {
        List<Integer> integers = Arrays.asList(nums);

        Collections.sort(integers, new Comparator<Integer>() {
                    //                    @Override 降序
//                    public int compare(Integer o1, Integer o2) {
//                        return o1<o2?1:-1;
//                    }
                    @Override //升序
                    public int compare(Integer o1, Integer o2) {
                        return o1 > o2 ? 1 : -1;
                    }
                }
        );

        System.out.println("integers = " + integers);
    }

    static void threeInt(Integer[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                    Integer temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
//                if(nums[i]>nums[j]){  //升序
//                    Integer temp = nums[i];
//                    nums[i]= nums[j];
//                    nums[j]=temp;
//                }
            }
        }
        System.out.println("降序 = " + Arrays.asList(nums));
    }

    static void four(Integer[] nums) {
        Map<Integer, Integer> nu = new HashMap<>();
//        Map<Integer,Integer> nuCopy = new HashMap<>();
        int nuMax = 0;
//        int nuCopyMax=0;
        for (int i = 0; i < nums.length; i++) {
            if (nu.containsKey(nums[i])) {
                nu.put(nums[i], nu.get(nums[i]) + 1);
                if (nu.get(nums[i]) > nuMax) {
                    nuMax = nu.get(nums[i]);
                }
            } else {
                nu.put(nums[i], 1);
            }
        }
//        for(int i=0;i<numsCopy.length;i++){
//            if(nuCopy.containsKey(numsCopy[i])){
//                nuCopy.put(numsCopy[i],nuCopy.get(numsCopy[i])+1);
//                if(nuCopyMax< nuCopy.get(numsCopy[i])){
//                   nuCopyMax = nuCopy.get(numsCopy[i]);
//                }
//            }else {
//                nuCopy.put(numsCopy[i],1);
//            }
//        }
        for (Integer i : nu.keySet()) {
            if (nu.get(i) == nuMax) {
                System.out.println("出现次数最多的元素 = " + i + ", 出现次数 = " + nu.get(i));
            }
        }
//        for (Integer i : nuCopy.keySet()){
//            if(nuCopy.get(i) == nuCopyMax){
//                System.out.println("numsCopy 出现次数最多的元素 = " + i+", 出现次数 = "+nuCopy.get(i));
//            }
//        }
    }

    static void fourInt(Integer[] nums) {
        Integer[][] nu = new Integer[1][2];
        int nuIndex = 0;
        int nuMax = 0;

        for (int i = 0; i < nums.length; i++) {
            boolean b = true;
            if (nuIndex == nu.length) {
                Integer[][] temp = new Integer[nuIndex + 1][2];
                int tempIndex = 0;
                for (int k = 0; k < nuIndex; k++) {
                    temp[tempIndex][0] = nu[k][0];
                    temp[tempIndex][1] = nu[k][1];
                    tempIndex++;
                }
                nu = temp;
                nuIndex = tempIndex;
            }
            for (int j = 0; j < nuIndex; j++) {
                if (nums[i].equals(nu[j][0])) {
                    b = false;
                    nu[j][1] = nu[j][1] + 1;
                    if (nu[j][1] > nuMax) {
                        nuMax = nu[j][1];
                    }
                }
            }
            if (b) {
                nu[nuIndex][0] = nums[i];
                nu[nuIndex][1] = 1;
                nuIndex++;
            }
        }
        for (int i = 0; i < nu.length; i++) {
            if (nu[i][1] == nuMax) {
                System.out.println("出现次数最多的元素 = " + nu[i][0] + ", 出现次数 = " + nu[i][1]);
            }
        }

    }

    static void five(Integer[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Iterator<Map.Entry<Integer, Integer>> iter = entryList.iterator();
        Map.Entry<Integer, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            System.out.println("数字：" + tmpEntry.getKey() + " - 出现次数" + tmpEntry.getValue());
        }
    }

    static void fiveInt(Integer[] nums) {
        Integer[][] temp = new Integer[1][2];
        int tempIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            boolean b = true;
            if (temp.length == tempIndex) {
                Integer[][] newTemp = new Integer[tempIndex + 1][2];
                int newIndex = 0;
                for (int j = 0; j < temp.length; j++) {
                    newTemp[newIndex][0] = temp[j][0];
                    newTemp[newIndex][1] = temp[j][1];
                    newIndex++;
                }
                temp = newTemp;
                tempIndex = newIndex;
            }
            for (int j = 0; j < temp.length; j++) {
                if (nums[i].equals(temp[j][0])) {
                    b = false;
                    temp[j][1] = temp[j][1] + 1;

                }
            }
            if (b) {
                temp[tempIndex][0] = nums[i];
                temp[tempIndex][1] = 1;
                tempIndex++;
            }
        }
        for (int i = 0; i < temp.length - 1; i++) {
            for (int j = i + 1; j < temp.length; j++) {
                if (temp[i][1] < temp[j][1]) {
                    Integer[][] newTemp = new Integer[1][2];
                    newTemp[0][0] = temp[i][0];
                    newTemp[0][1] = temp[i][1];
                    temp[i][0] = temp[j][0];
                    temp[i][1] = temp[j][1];
                    temp[j][0] = newTemp[0][0];
                    temp[j][1] = newTemp[0][1];
                }
            }
        }
        for (int i = 0; i < tempIndex; i++) {
            System.out.println("数字： " + temp[i][0] + " - 出现次数" + temp[i][1]);
        }
    }


}
