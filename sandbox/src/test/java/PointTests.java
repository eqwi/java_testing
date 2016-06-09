import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void distanceTest1() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(5, 8);
        Assert.assertEquals(p1.distance(p1, p2), Math.sqrt(20));
    }

    @Test
    public void distanceTest2() {
        Point p1 = new Point(4, 5);
        Point p2 = new Point(8, 8);
        Assert.assertEquals(p1.distance(p1, p2), 5.0);
    }

}
