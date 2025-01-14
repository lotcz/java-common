package eu.zavadil.java;

import eu.zavadil.java.util.StringUtils;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UrlBuilder {

	private String schema = "http";

	public String getSchema() {
		return this.schema;
	}

	private final boolean encodeQueryParams;

	private boolean trailingSlash = false;

	private ArrayList<String> path = new ArrayList<>();

	private HashMap<String, String> query = new HashMap<>();

	public UrlBuilder(boolean encodeQueryParams) {
		this.encodeQueryParams = encodeQueryParams;
	}

	public UrlBuilder() {
		this(true);
	}

	public UrlBuilder(String url, boolean encodeQueryParams) {
		this(encodeQueryParams);

		List<String> schemaAndRest = StringUtils.safeSplit(url, "://");
		if (schemaAndRest.isEmpty()) {
			throw new RuntimeException("String was empty, cannot build url!");
		}
		if (schemaAndRest.size() > 2) {
			throw new RuntimeException(String.format("String %s is not a valid url!", url));
		}
		if (schemaAndRest.size() == 2) {
			this.schema = schemaAndRest.get(0);
		}
		List<String> pathAndQuery = StringUtils.safeSplit(schemaAndRest.get(schemaAndRest.size() - 1), "\\?");
		if (pathAndQuery.isEmpty() || pathAndQuery.size() > 2) {
			throw new RuntimeException(String.format("String %s is not a valid url!", url));
		}
		this.addPath(pathAndQuery.get(0));
		if (pathAndQuery.size() == 2) {
			List<String> queryParams = StringUtils.safeSplit(pathAndQuery.get(1), "&");
			queryParams.forEach(
				q -> {
					List<String> param = StringUtils.safeSplit(q, "=");
					if (param.isEmpty()) return;
					if (param.size() > 2) {
						throw new RuntimeException(String.format("String %s is not a query param!", q));
					}
					String key = param.get(0);
					String value = param.get(param.size() - 1);
					if (this.encodeQueryParams) {
						value = UrlBuilder.decodeQueryString(value);
					}
					this.addQuery(key, value);
				}
			);
		}

	}

	public UrlBuilder(String url) {
		this(url, true);
	}

	public UrlBuilder schema(String schema) {
		this.schema = schema;
		return this;
	}

	public String getDomainName() {
		if (this.path.isEmpty()) return null;
		return this.path.get(0);
	}

	public String getDomainAndSchema() {
		return String.format("%s://%s", this.schema, this.getDomainName());
	}

	public UrlBuilder addPath(String path) {
		if (StringUtils.isEmpty(path)) return this;
		this.trailingSlash = path.endsWith("/");
		this.path.addAll(StringUtils.safeSplit(path, "/").stream().filter(StringUtils::notEmpty).toList());
		return this;
	}

	public UrlBuilder addQuery(String key, String value) {
		this.query.put(key, value);
		return this;
	}

	public UrlBuilder addQuery(String key, int value) {
		this.query.put(key, String.valueOf(value));
		return this;
	}

	public static UrlBuilder of(String url) {
		return new UrlBuilder(url);
	}

	public static String encodeQueryString(String str) {
		return URLEncoder.encode(str, StandardCharsets.UTF_8);
	}

	public static String decodeQueryString(String str) {
		return URLDecoder.decode(str, StandardCharsets.UTF_8);
	}

	private String getPathString() {
		String strPath = String.join("/", this.path);
		return this.trailingSlash ? strPath + "/" : strPath;
	}

	private String getQueryString() {
		return String.join(
			"&",
			this.query
				.keySet()
				.stream()
				.map(
					key -> String.format(
						"%s=%s",
						key,
						this.encodeQueryParams ? encodeQueryString(this.query.get(key)) : this.query.get(key)
					)
				)
				.toList()
		);
	}

	public String buildAsString() {
		String baseUrl = String.format("%s://%s", this.schema, this.getPathString());
		if (this.query.isEmpty()) return baseUrl;
		return String.join("?", baseUrl, this.getQueryString());
	}

	public URL build() {
		String url = this.buildAsString();
		try {
			return URI.create(url).toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(String.format("Error when building URL from: %s", url), e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(String.format("Error when converting %s to URL", url), e);
		}
	}

}
