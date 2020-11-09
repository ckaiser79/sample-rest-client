package de.servicezombie.samples.xkcd_openapi;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		final DefaultApi impl1 = new DefaultApiDummyImpl();
		final Info0JsonApi impl2 = new DefaultApiDummyImpl();

		XkcdComicInfo info;

		info = impl1.getReviewById(2L);
		System.out.println(info);

		info = impl2.getLastReview();
		System.out.println(info);

	}
}
