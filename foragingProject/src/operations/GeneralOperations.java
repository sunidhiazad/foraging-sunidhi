package operations;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeneralOperations {
	public static double getDoubleWithPrecision(double value, int precision) {
		return new BigDecimal(value).setScale(precision, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	public static BigDecimal divideBD(BigDecimal num, BigDecimal den) {
		return num.divide(den, MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_EVEN);
	}

	public static BigDecimal divideBD(int num, int den) {
		BigDecimal bd_num = new BigDecimal(num);
		BigDecimal bd_den = new BigDecimal(den);
		return bd_num.divide(bd_den, MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_EVEN);
	}

	public static int getLog(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}

	public static Color getRandomLightColor() {
		return new Color(Color.HSBtoRGB((float) Math.random(), (float) Math.random(), 0.5F + ((float) Math.random()) / 2F));
	}

	public static Color getRandomBrightColor() {
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = 1.0f; // 1.0 for brilliant, 0.0 for dull
		final float luminance = 1.0f; // 1.0 for brighter, 0.0 for black
		return Color.getHSBColor(hue, saturation, luminance);
	}

	public static <T> void displayCollection(Collection<T> list) {
		System.out.println("***** PRINTING LIST *****");
		for (T item : list) {
			System.out.println(item);
		}
	}

	public static BigDecimal tanInverse(BigDecimal value) {
		return new BigDecimal(Math.toDegrees(Math.atan(value.doubleValue()))).setScale(2, RoundingMode.HALF_EVEN);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map sortMapByValue(Map unsortMap) {
		List list = new LinkedList(unsortMap.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map sortMapByValueAsc(Map unsortMap) {
		List list = new LinkedList(unsortMap.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o2, Object o1) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static List<String> tokenize(String line, String tokenizer) {
		return Arrays.asList(line.split(tokenizer));
	}

	public static void printMap(Map<?, ?> map) {
		System.out.println("***** PRINTING MAP *****");
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}

	public static BigDecimal getDeviation(List<Integer> list) {
		BigDecimal avg = getAverage(list);
		BigDecimal var = new BigDecimal(0);
		for (Integer i : list) {
			BigDecimal x = new BigDecimal(i).subtract(avg);
			var = var.add(x.pow(2, MathContext.DECIMAL32)).setScale(2, RoundingMode.HALF_EVEN);
		}
		var = divideBD(var, new BigDecimal(list.size() - 1));
		return new BigDecimal(Math.sqrt(var.doubleValue())).setScale(2, RoundingMode.HALF_EVEN);

	}

	public static BigDecimal getAverage(List<Integer> list) {
		int sum = 0;
		for (Integer i : list) {
			sum += i;
		}
		return divideBD(sum, list.size());
	}

	public static List<List<Integer>> getPermutations(List<Integer> input) {
		List<List<Integer>> output = new ArrayList<List<Integer>>();
		if (input.isEmpty()) {
			output.add(new ArrayList<Integer>());
			return output;
		}
		List<Integer> list = new ArrayList<Integer>(input);
		Integer head = list.get(0);
		List<Integer> rest = list.subList(1, list.size());
		for (List<Integer> permutations : getPermutations(rest)) {
			List<List<Integer>> subLists = new ArrayList<List<Integer>>();
			for (int i = 0; i <= permutations.size(); i++) {
				List<Integer> subList = new ArrayList<Integer>();
				subList.addAll(permutations);
				subList.add(i, head);
				subLists.add(subList);
			}
			output.addAll(subLists);
		}
		return output;
	}

}
