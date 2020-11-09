package de.servicezombie.samples.xkcd_tranfer;

import de.servicezombie.samples.xkcd_transfer.XkcdComInfoEndpoint;
import de.servicezombie.samples.xkcd_transfer.XkcdComicInfo;

public class XkcdComInfoEndpointDummyImpl implements XkcdComInfoEndpoint {

	@Override
	public XkcdComicInfo info() {
		return info(20);
	}

	@Override
	public XkcdComicInfo info(int id) {
		System.out.println("client-plain implements JAX-RS Interface successfully!");
		final XkcdComicInfo result = new XkcdComicInfo();
		result.setDay(id);
		result.setYear(id);
		result.setMonth(id);
		return result;
	}

}
