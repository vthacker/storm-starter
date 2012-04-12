package storm.starter;

import storm.starter.spout.TwitterSampleSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.starter.bolt.PrinterBolt;

public class PrintSampleStream {
	
	/**
	 * To run this example go to:
	 * https://dev.twitter.com and create a application, once created the parameters that need to be provided are
	 * @param args[0] Consumer key
	 * @param args[1] Consumer secret 
	 * @param args[2] Access token
	 * @param args[3] Access token secret
	 */
    public static void main(String[] args) {
        String accessToken = args[0];
        String accessTokenSecret = args[1];
        String consumerKey = args[2];
        String consumerKeySecret = args[3];
        
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new TwitterSampleSpout(accessToken, accessTokenSecret, consumerKey, consumerKeySecret));
        builder.setBolt("print", new PrinterBolt())
                .shuffleGrouping("spout");
                
        
        Config conf = new Config();
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
