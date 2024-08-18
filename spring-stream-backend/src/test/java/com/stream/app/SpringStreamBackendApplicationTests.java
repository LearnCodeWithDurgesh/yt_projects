package com.stream.app;

import com.stream.app.services.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringStreamBackendApplicationTests {

	@Autowired
	VideoService videoService;

	@Test
	void contextLoads() {

//		videoService.processVideo("499253fc-78aa-48e1-9d20-06130f550568",null);

	}

}

//ffmpeg -i videos/852421-hd_1920_1080_30fps.mp4 -map 0:v -map 0:a -s:v:0 640x360 -b:v:0 800k -map 0:v -map 0:a -s:v:1 1280x720 -b:v:1 2800k -map 0:v -map 0:a -s:v:2 1920x1080 -b:v:2 5000k -var_stream_map "v:0,a:0 v:1,a:0 v:2,a:0" -master_pl_name videos_hsl/083f066b-0546-4c84-b569-88894b30b61c/master.m3u8 -f hls -hls_time 10 -hls_list_size 0 -hls_segment_filename "videos_hsl/083f066b-0546-4c84-b569-88894b30b61c/v%v/fileSequence%d.ts" "videos_hsl/083f066b-0546-4c84-b569-88894b30b61c/v%v/prog_index.m3u8"

