import java.util.Map;

public class addEdges {
    public static TramNetwork CreateTramNetwork(CSVreader MetroLinkTimes,Map<String,Double> delays){
        TramNetwork TramNetwork=new TramNetwork(delays);
        for (int i=0;i<MetroLinkTimes.get_from().size();i++) {
            TramNetwork.add_edge(MetroLinkTimes.get_from().get(i), MetroLinkTimes.get_to().get(i), MetroLinkTimes.get_time().get(i), MetroLinkTimes.get_line().get(i));
            // adds all of the stations and their edges to the tram network graph
        }
        return TramNetwork;
        // returns the tram network in order to perform a dijkstras search
    }
}
