import org.junit.Assert;
import org.junit.Test;

public class ClosedHashSetTest2 {
    @Test
    public void testDeleteWhenSameHashAfter() { //-337170
        String[] toHash = {"3630", "23", "186890", "402985"};
        ClosedHashSet closedHashSet = new ClosedHashSet();
        for (String h : toHash) {
            closedHashSet.add(h);
        }
        for (String h : toHash) {
            Assert.assertTrue(closedHashSet.contains(h));
        }
        closedHashSet.delete("3630");
        Assert.assertFalse(closedHashSet.contains("3630"));
        for (int i = 1; i < toHash.length; i++) {
            Assert.assertTrue(closedHashSet.contains(toHash[i]));
        }
    }

    @Test
    public void testContainsInFullTable() {
        String[] toHash = {"3630", "186890", "-337170", "402985", "520113", "534754", "666523", "750376", "882145", "896786", "-915297", "-1019169", "-1406611", "-1622233", "-1636874", "-1754002"};
        ClosedHashSet closedHashSet = new ClosedHashSet(1, 0);
        for (String h : toHash) {
            closedHashSet.add(h);
        }
        for (String h : toHash) {
            Assert.assertTrue(closedHashSet.contains(h));
        }
    }

//    @Test
//    public void testDeleteNonExistentInFullTable() {
//        String[] toHash = {"3630", "186890", "-337170", "402985", "520113", "534754", "666523", "750376", "882145", "896786", "-915297", "-1019169", "-1406611", "-1622233", "-1636874", "-1754002"};
//        ClosedHashSet closedHashSet = new ClosedHashSet(1, 0);
//        for (String h : toHash) {
//            closedHashSet.add(h);
//        }
//        Assert.assertTrue(closedHashSet.size() == closedHashSet.capacity());
//        Assert.assertFalse(closedHashSet.contains("-1768643"));
//        Assert.assertFalse(closedHashSet.delete("-1768643"));
//    }
}
