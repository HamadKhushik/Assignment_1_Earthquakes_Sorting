import java.util.ArrayList;

public class LargestQuakes {

    public void findLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        /*for (QuakeEntry qe : list) {
            System.out.println(qe);
        }*/
        System.out.println("read data for " + list.size());
        /* test indexOfLargest()
        int maxMag = indexOfLargest(list);
        System.out.println("The index of earthquake with largest magnitude is " + maxMag);
        System.out.println("The largest magnitude earthquake is " + list.get(maxMag)); */

        ArrayList<QuakeEntry> largest = getLargest(list, 50);
        for (QuakeEntry qe : largest) {
            System.out.println(qe);
        }
    }

    public int indexOfLargest(ArrayList<QuakeEntry> data) {
        int largest = 0;
        Double currMag = null;
        for (QuakeEntry qe : data) {
            if (currMag == null) {
                currMag = qe.getMagnitude();
                largest = data.indexOf(qe);
            } else if (qe.getMagnitude() > currMag) {
                currMag = qe.getMagnitude();
                largest = data.indexOf(qe);
            }
        }
        return largest;
    }

    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> ans = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        for (int k = 0; k < howMany; k++) {
            double max = 0;
            int index = 0;
            for (int j = 0; j < copy.size(); j++) {
                if (copy.get(j).getMagnitude() > max) {
                    max = copy.get(j).getMagnitude();
                    index = j;
                }
            }
            ans.add(copy.get(index));
            copy.remove(index);
        }
        return ans;
    }

    public static void main(String[] args) {
        LargestQuakes lq = new LargestQuakes();
        lq.findLargestQuakes();
    }
}
