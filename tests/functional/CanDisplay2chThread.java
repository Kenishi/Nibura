package functional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import nibura.logic.Board;
import nibura.logic.BoardList;
import nibura.logic.InvalidSuiteTypeException;
import nibura.logic.InvalidThreadException;
import nibura.logic.NichThreadFetcher.PostParsingException;
import nibura.logic.ParsingErrorException;
import nibura.logic.Post;
import nibura.logic.Thread;
import nibura.logic.ThreadLink;
import nibura.logic.BoardListElement.SuiteType;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import functional.ApacheServer.UnknownOSException;

public class CanDisplay2chThread {
	private BoardList boardList = null;
	private Board board = null;
	private ThreadLink threadLink = null;
	private Thread thread = null;

	@BeforeClass
	public static void setupOnce() throws IOException, UnknownOSException {
		ApacheServer.createServerInstance();
	}
	@AfterClass
	public static void teardownOnce() throws IOException, UnknownOSException {
		ApacheServer.createServerInstance().exit();
	}
	
	@Before
	public void setUp() throws Exception {
		boardList = null;
		board = null;
		threadLink = null;
		thread = null;		
	}

	@Test(expected=InvalidThreadException.class)
	public void shouldFailCorrectly() throws MalformedURLException, InvalidSuiteTypeException, ParsingErrorException, PostParsingException {
		// Setup
		threadLink = new ThreadLink("Test","http://www.google.com",424,SuiteType.NICH_SUITE);
		
		// Test
		Thread thread = new Thread(threadLink);
	}
	
	@Test
	public void shouldParseTestThreadHTML() throws ParseException, IOException, InvalidSuiteTypeException, ParsingErrorException, PostParsingException {
		// Setup
		URL url = TestResources.NICH_LIVE_THREAD_HTML.getLocalURL();
		
		threadLink = new ThreadLink("TestLink", url, 111, SuiteType.NICH_SUITE);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd(E) HH:mm:ss.S", Locale.JAPAN);
		
		// Setup Expected
		Post expectedPost1 = new Post(1,
				"風の谷の名無しさん＠実況は実況板で",
				"sage",
				dateFormat.parse("2014/01/12(日) 11:46:10.02"),
				"ewlzMYA40",
				"お兄ちゃんとLOVE×LOVEしないと死んじゃう！？" 
				+ "======================="
				+ "･【※実況厳禁】→アニメ特撮実況板：http://hayabusa.2ch.net/liveanime/"
				+ "･著作権法の精神に照らして､投稿動画(公式配信を除く｡)に関する話題･URL貼りは厳禁｡"
				+ "・荒らし、煽りは徹底放置。→削除依頼：http://qb5.2ch.net/saku/"
				+ "・2chブラウザ(無料、「人大杉」回避)の導入を推奨。→http://monazilla.org/index.php?e=109"
				+ "・sage進行推奨。E-mail欄(メール欄／メ欄)に半角小文字で「sage」と記入。"
				+ "・次スレは>>950が宣言してから立てる事。無理ならば代理人を指名する事。"
				+ "========================"
				+ "●放映及び配信日程―平成26年1月より放送開始"
				+ "・東京MXテレビ (MX)、サンテレビ (SUN)　毎週土曜日 22:30～　1月4日～"
				+ "・アニメシアターX (AT-X)　毎週日曜日 20:30～　1月5日～"
				+ "　　毎週(火) 11:30～、毎週(木) 29:30～、毎週(土) 15:30～"
				+ "・日本BS放送 (BS11)　　　毎週木曜日 27:00～　1月9日～"
				+"・ニコニコ動画（公式配信） 毎週金曜日 12:00更新　1月10日～：http://ch.nicovideo.jp/imocyo-anime"
				+ "\n"
				+ "●関連URL"
				+ "・番組公式サイト：http://www.imocyo-anime.com/"
				+ "・番組公式Twitter：http://twitter.com/imocyo_anime"
				+ "・原作漫画公式サイト：http://www.fujimishobo.co.jp/sp/201108imocho/"
				+ "\n"
				+ "●前スレ"
				+ "最近、妹のようすがちょっとおかしいんだが。 4"
				+ "http://toro.2ch.net/test/read.cgi/anime/1389109109/");
		
		Post expectedPost2 = new Post(367,
				"風の谷の名無しさん＠実況は実況板で",
				"sage",
				dateFormat.parse("2014/01/12(日) 21:43:46.97"),
				"GN0yVNSrP",
				"こういうのはオリジナルエロが入るから大丈夫");
		
		Post expectedPost3 = new Post(378,
				"USER",
				"sage",
				dateFormat.parse("1970/01/01(日) 00:00:00.00"),
				"GN0yVNSrP",
				"POST_TEXT");
		
		
		// Exercise 
		Thread thread = new Thread(threadLink);
		Post post1 = thread.getPost(1);
		Post post2 = thread.getPost(367);
		Post post3 = thread.getPost(368);
		
		// Test
		Assert.assertEquals(expectedPost1, post1);
		Assert.assertEquals(expectedPost2, post2);
		Assert.assertEquals(expectedPost3, post3);		
	}
	
	@Test
	public void shouldParseLiveThread() {
		
	}
	
	

}
