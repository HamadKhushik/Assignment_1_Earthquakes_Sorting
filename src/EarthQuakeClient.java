import java.util.ArrayList;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
                                                   double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
                                                      double distMax,
                                                      Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
                                               double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (qe.getDepth() > minDepth && qe.getDepth() < maxDepth) {
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
                                                String named, String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            if (named.equals("end") && qe.getInfo().endsWith(phrase)) {
                answer.add(qe);
            } else if (named.equals("start") && qe.getInfo().startsWith(phrase)) {
                answer.add(qe);
            } else if (named.equals("any") && qe.getInfo().contains(phrase)) {
                answer.add(qe);
            }
        }
        return answer;
    }


    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for (QuakeEntry qe : list) {
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                    qe.getLocation().getLatitude(),
                    qe.getLocation().getLongitude(),
                    qe.getMagnitude(),
                    qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        ArrayList<QuakeEntry> mag = filterByMagnitude(list, 5.0);
        System.out.println("The Earthquakes with magnitudes greater than 5.0 are " + mag.size());
        for (QuakeEntry qe : mag) {
            System.out.println(qe);
        }

    }

    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");

        // This location is Durham, NC
        // Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city = new Location(38.17, -118.82);

        ArrayList<QuakeEntry> mag = filterByDistanceFrom(list, 1000000, city);
        System.out.println("The number of quakes that match the criteria are : " + mag.size());
        for (QuakeEntry qe : mag) {
            System.out.println(qe.getLocation().distanceTo(city) + "  " + qe.getInfo());
        }
    }

    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        ArrayList<QuakeEntry> mag = filterByDepth(list, -4000.0, -2000.0);
        System.out.println("The number of quakes found matching the criteria are : " + mag.size());
        for (QuakeEntry qe : mag) {
            System.out.printf("%4.2f,%4.2f,%4.1f,%5.2f,\t%s\n", qe.getLocation().getLatitude(), qe.getLocation().getLongitude()
                    , qe.getMagnitude(), qe.getDepth(), qe.getInfo());
        }
    }

    public ArrayList<QuakeEntry> getData() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        return list;
    }

    public void quakesByPhrase() {
        ArrayList<QuakeEntry> list = getData();
        ArrayList<QuakeEntry> mag = filterByPhrase(list, "any", "Can");
        System.out.println("The number of Quakes matching the criteria are : " + mag.size());
        for (QuakeEntry qe : mag) {
            System.out.printf("%4.2f,%4.2f,%4.1f,%5.2f,\t%s\n", qe.getLocation().getLatitude(),
                    qe.getLocation().getLongitude(), qe.getMagnitude(), qe.getDepth(),
                    qe.getInfo());
        }
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
        /* test filterByMagnitude
        System.out.println("Earthquakes with magnitude greater than magMin are: ");
        ArrayList<QuakeEntry> mag = filterByMagnitude(list, 5.0);
        return mag;
        */
    }

    public static void main(String[] args) {
        EarthQuakeClient eq = new EarthQuakeClient();
        /* Test createCSV()
        eq.createCSV();
         */

        /* test filterByMagnitude
        ArrayList<QuakeEntry> data = eq.createCSV();
        for (QuakeEntry qe : data) {
            System.out.println(qe);
        }
        */

        //test bigQuakes()
        //eq.bigQuakes();

        //test closeToMe()
        //eq.closeToMe();

        //test quakeOfDepth()
        //eq.quakesOfDepth();

        //test quakesByPhrase()
        eq.quakesByPhrase();

    }
}



