package de.servicezombie.samples.xkcd_openapi;

public class DefaultApiDummyImpl implements DefaultApi, Info0JsonApi {

	@Override
	public XkcdComicInfo getReviewById(Long comicId) {
		return new XkcdComicInfo();
	}

	@Override
	public XkcdComicInfo getLastReview() {
		return new XkcdComicInfo();
	}

}
