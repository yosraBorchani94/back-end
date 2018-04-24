
package org.sid.liveBroadcast;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;

import org.codehaus.groovy.ast.stmt.LoopingStatement;
import org.sid.entities.Event;
import org.sid.liveBroadcast.Auth;
import org.springframework.stereotype.Service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CreateBroadcast {

	private static YouTube youtube;
	public String insert(Event event) {
		

		DateTime startdateTime = new DateTime(event.getStartDate());
		DateTime enddateTime = new DateTime(event.getEndDate());

		// This OAuth 2.0 access scope allows for full read/write access to the
		// authenticated user's account.
		
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
	
		
		try {

			// Authorize the request.
		
		
			Credential credential = Auth.authorize(scopes, "createbroadcast");
			
			// This object is used to make YouTube Data API requests.
		    System.out.println("credential " +credential);
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
					.setApplicationName("youtube-cmdline-createbroadcast-sample").build();
		
			// Prompt the user to enter a title for the broadcast.
			String title = event.getTitle();
			// Create a snippet with the title and scheduled start and end
			// times for the broadcast.
			
			LiveBroadcastSnippet broadcastSnippet = new LiveBroadcastSnippet();
			broadcastSnippet.setTitle(title);
			broadcastSnippet.setScheduledStartTime(startdateTime);
			broadcastSnippet.setScheduledEndTime(enddateTime);

			// Set the broadcast's privacy status to "unlisted". See:
			// https://developers.google.com/youtube/v3/live/docs/liveBroadcasts#status.privacyStatus
			LiveBroadcastStatus status = new LiveBroadcastStatus();
			status.setPrivacyStatus("unlisted");

			LiveBroadcast broadcast = new LiveBroadcast();
			broadcast.setKind("youtube#liveBroadcast");
			broadcast.setSnippet(broadcastSnippet);
			broadcast.setStatus(status);

			// Construct and execute the API request to insert the broadcast.
			YouTube.LiveBroadcasts.Insert liveBroadcastInsert = youtube.liveBroadcasts().insert("snippet,status",
					broadcast);
			LiveBroadcast returnedBroadcast = liveBroadcastInsert.execute();

			// Print information from the API response.
			System.out.println("\n================== Returned Broadcast ==================\n");
			System.out.println("  - Id: " + returnedBroadcast.getId());
			System.out.println("  - Title: " + returnedBroadcast.getSnippet().getTitle());
			System.out.println("  - Description: " + returnedBroadcast.getSnippet().getDescription());
			System.out.println("  - Published At: " + returnedBroadcast.getSnippet().getPublishedAt());
			System.out.println("  - Scheduled Start Time: " + returnedBroadcast.getSnippet().getScheduledStartTime());
			System.out.println("  - Scheduled End Time: " + returnedBroadcast.getSnippet().getScheduledEndTime());

			// Prompt the user to enter a title for the video stream.
			title = event.getTitle();
			System.out.println("You chose " + title + " for stream title.");

			// Create a snippet with the video stream's title.
			LiveStreamSnippet streamSnippet = new LiveStreamSnippet();
			streamSnippet.setTitle(title);

			// Define the content distribution network settings for the
			// video stream. The settings specify the stream's format and
			// ingestion type. See:
			// https://developers.google.com/youtube/v3/live/docs/liveStreams#cdn
			CdnSettings cdnSettings = new CdnSettings();
			cdnSettings.setFormat("1080p");
			cdnSettings.setIngestionType("rtmp");

			LiveStream stream = new LiveStream();
			stream.setKind("youtube#liveStream");
			stream.setSnippet(streamSnippet);
			stream.setCdn(cdnSettings);

			// Construct and execute the API request to insert the stream.
			YouTube.LiveStreams.Insert liveStreamInsert = youtube.liveStreams().insert("snippet,cdn", stream);
			LiveStream returnedStream = liveStreamInsert.execute();

			// Print information from the API response.
			System.out.println("\n================== Returned Stream ==================\n");
			System.out.println("  - Id: " + returnedStream.getId());
			System.out.println("  - Title: " + returnedStream.getSnippet().getTitle());
			System.out.println("  - Description: " + returnedStream.getSnippet().getDescription());
			System.out.println("  - Published At: " + returnedStream.getSnippet().getPublishedAt());

			// Construct and execute a request to bind the new broadcast
			// and stream.
			YouTube.LiveBroadcasts.Bind liveBroadcastBind = youtube.liveBroadcasts().bind(returnedBroadcast.getId(),
					"id,contentDetails");
			liveBroadcastBind.setStreamId(returnedStream.getId());
			returnedBroadcast = liveBroadcastBind.execute();

			// Print information from the API response.
			System.out.println("\n================== Returned Bound Broadcast ==================\n");
			System.out.println("  - Broadcast Id: " + returnedBroadcast.getId());
			System.out.println("  - Bound Stream Id: " + returnedBroadcast.getContentDetails().getBoundStreamId());

			return returnedBroadcast.getId();

		} catch (GoogleJsonResponseException e) {
			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
			return 0+ "";

		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
			return 0+"";
		} catch (Throwable t) {
			System.err.println("Throwable: " + t.getMessage());
			t.printStackTrace();
			return 0+"";
		}

	}

//	public void update(Event event) {
//		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
//		try {
//			// Authorize the request.
//			Credential credential = Auth.authorize(scopes, "updatebroadcast");
//			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
//					.setApplicationName("youtube-cmdline-updatebroadcast-sample").build();
//			YouTube.LiveBroadcasts.List liveBroadcastRequest = youtube.liveBroadcasts().list("snippet")
//					.setId(event.getId() + "");
//			LiveBroadcastListResponse returnedListResponse = liveBroadcastRequest.execute();
//			List<LiveBroadcast> returnedList = returnedListResponse.getItems();
//			System.out.println("\n================== Returned Broadcasts ==================\n");
//			for (LiveBroadcast broadcast : returnedList) {
//				System.out.println("  - Id: " + broadcast.getId());
//				System.out.println("  - Title: " + broadcast.getSnippet().getTitle());
//				System.out.println("  - Scheduled Start Time: " + broadcast.getSnippet().getScheduledStartTime());
//				System.out.println("  - Scheduled End Time: " + broadcast.getSnippet().getScheduledEndTime());
//			}
//		} catch (GoogleJsonResponseException e) {
//			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
//					+ e.getDetails().getMessage());
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.err.println("IOException: " + e.getMessage());
//			e.printStackTrace();
//		} catch (Throwable t) {
//			System.err.println("Throwable: " + t.getMessage());
//			t.printStackTrace();
//		}
//	}
}
