public class NewProgram {
	
	public static void main (String[] args) {

		Point p1 = new Point(2, 3);
		Point p2 = new Point(4, 5);

		System.out.println("1. Расстояние между точками p1 и p2: " + distance(p1, p2)); // Пример использования функции

		System.out.println("2. Расстояние между точками p1 и p2: " + p1.distance(p1, p2)); // Пример использовтания метода в классе

	}

	public static double distance(Point p1, Point p2) {
		return Math.sqrt((Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2)));
	}
}