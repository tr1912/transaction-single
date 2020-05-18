package com.wx.inspect.transaction.single.util;

import com.alibaba.fastjson.JSON;
import com.wx.inspect.transaction.single.base.Result;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static SSLConnectionSocketFactory sslsf = null;

	public static Map<String,String> getHearderMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		return map;
	}
	/**
	 * 连接超时毫秒数
	 */
	public static final int ConnectionTimeout = 60000;

	/**
	 * 读取超时毫秒数
	 */
	public static final int ReadTimeout = 180000;

	public static String post(String url, Object params) throws KeyManagementException, NoSuchAlgorithmException, IOException, IllegalArgumentException, IllegalAccessException {
		Map<String, String> pms = null;
		if (params instanceof Map) {
			Map<?, ?> pm = (Map<?, ?>) params;
			pms = new HashMap<>(pm.size());
			Iterator<?> it = pm.entrySet().iterator();
			while (it.hasNext()) {
				Entry<?, ?> me = (Entry<?, ?>) it.next();
				pms.put(me.getKey().toString(), me.getValue().toString());
			}
		} else {
			Class<? extends Object> c = params.getClass();
			Field[] fs = c.getDeclaredFields();
			pms = new HashMap<>(fs.length);
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				if (Modifier.isFinal(f.getModifiers()))
					continue;
				f.setAccessible(true);
				Object v = f.get(params);
				if (v != null)
					pms.put(f.getName(), v.toString());
			}
		}
		return post(url, pms, null);
	}

	public static String post(String url, Map<String, String> params, Map<String, String> heads)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {
		CloseableHttpClient httpclient = HttpUtil.createHttpClient(url);
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		if (heads != null) {
			for (Entry<String, String> entry : heads.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(600000).setConnectionRequestTimeout(600000)
				.setSocketTimeout(600000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		return EntityUtils.toString(response.getEntity());
	}

	public static void main(String[]  args){

	}

	/**
	 * 发送HTTPS	POST请求
	 *
	 * @param   访问的HTTPS地址,POST访问的参数Map对象
	 * @return  返回响应值
	 * @author wx
	 * */
	public static final String sendHttpsRequestByPost(String url, Map<String, String> params, Map<String, String> heads) {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		//创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			@Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			@Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			@Override
            public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		//这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
            public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			@Override
            public void verify(String arg0, SSLSocket arg1) throws IOException {}
			@Override
            public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
			@Override
            public void verify(String arg0, X509Certificate arg1) throws SSLException {}
		};
		try {
			//TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			//使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			//创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			//通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			HttpPost httpPost = new HttpPost(url);
            if (heads != null) {
                for (Entry<String, String> entry : heads.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
			// 构建POST请求的表单参数
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (KeyManagementException e) {
			logger.error("出现异常",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("出现异常",e);
		} catch (UnsupportedEncodingException e) {
			logger.error("出现异常",e);
		} catch (ClientProtocolException e) {
			logger.error("出现异常",e);
		} catch (ParseException e) {
			logger.error("出现异常",e);
		} catch (IOException e) {
			logger.error("出现异常",e);
		} finally {
			// 关闭连接,释放资源
			// httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}

	public static String postJson(String url, String json, Map<String, String> headers) {
		URL u = null;
		HttpURLConnection con = null;
		OutputStreamWriter osw = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		sb.append(json);
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			// // POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			//con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			con.setConnectTimeout(100000);
			con.setReadTimeout(100000);
			for (String header : headers.keySet()) {
				con.setRequestProperty(header, headers.get(header));
			}

			osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			logger.error("出现异常",e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			logger.error("出现异常",e);
		}

		return buffer.toString();
	}

	public static String postJson(String url, String json) {
		return postJson(url, json, new HashMap<String, String>(0));
	}
	public static String postJsonToBody(String url, Object object) {
		return postJsonToRequestBody(url, JSON.toJSONString(object), new HashMap<String, String>(0));
	}


	public static String postJson(String url, Object object) {
		return postJson(url, JSON.toJSONString(object), new HashMap<String, String>(0));
	}

	private static CloseableHttpClient createHttpClient(String url)
			throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException {
		URL u = new URL(url);
		CloseableHttpClient httpclient = null;
		if ("https".equals(u.getProtocol())) {
			logger.trace("https");
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} else {
			logger.trace("http");
			httpclient = HttpClients.createDefault();
		}

		return httpclient;
	}

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("出现异常",e);
		}
		return result;
	}
	/**
	 * JavaBean对象转化成Map对象
	 *
	 * @param javaBean
	 * @return
	 * @author jqlin
	 */
	public static Map<String,String> javaToMap(Object javaBean) {
		Map<String,String> map = new HashMap<>();
		try {
			// 获取javaBean属性
			BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			if (propertyDescriptors != null && propertyDescriptors.length > 0) {
				String propertyName = null; // javaBean属性名
				Object propertyValue = null; // javaBean属性值
				for (PropertyDescriptor pd : propertyDescriptors) {
					propertyName = pd.getName();
					if (!propertyName.equals("class")) {
						Method readMethod = pd.getReadMethod();
						propertyValue = readMethod.invoke(javaBean, new Object[0]);
						if(propertyValue != null  && !"".equals(propertyValue)){
							map.put(propertyName, String.valueOf(propertyValue));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("出现异常",e);
		}
		return map;
	}

	public static String post(String url, Object object, Map<String, String> heads)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {
		CloseableHttpClient httpclient = HttpUtil.createHttpClient(url);
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Map<String, String> params = javaToMap(object);
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		if (heads != null) {
			for (Entry<String, String> entry : heads.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		return EntityUtils.toString(response.getEntity());
	}

	public static String getResponse(Map<String, String> params, String url, Map<String, String> heads) {
		logger.info("访问url:{},参数:{}",url,params);
		long start= System.currentTimeMillis();
		String response=null;
		try {
			response= HttpUtil.post(url, params, heads);
		} catch (Exception e) {
			logger.error("发生异常：",e);
		}
		long end= System.currentTimeMillis();

		logger.info("接口调用耗时:{}ms,接口返回结果：{}",(end-start),response);
		return response;
	}

	public static String postForm(String url, String json, Map<String, String> headers) {
		URL u = null;
		HttpURLConnection con = null;
		OutputStreamWriter osw = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		sb.append(json);
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			// // POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setConnectTimeout(10000);
			if(headers != null){
				for (String header : headers.keySet()) {
					con.setRequestProperty(header, headers.get(header));
				}
			}
			osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			logger.error("出现异常",e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			logger.error("出现异常",e);
		}

		return buffer.toString();
	}

	/**
	 * @Author
	 * @Date 2018/5/10
	 * 发送get请求
	 * @param url    路径
	 * @return
	 */
	public static String httpGet(String url){
		//get请求返回结果
		String jsonResult = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,ConnectionTimeout);
			HttpConnectionParams.setSoTimeout(httpParams,ReadTimeout);
			client.setParams(httpParams);
			//发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/**请求发送成功，并得到响应**/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**读取服务器返回过来的json字符串数据**/
				jsonResult = EntityUtils.toString(response.getEntity());
			} else {
				jsonResult = Result.getResult("ERROR","HttpStatus:"+response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			jsonResult = Result.getResult("ERROR","HttpStatus:"+e.getMessage());
		}
		return jsonResult;
	}

	public static String uploadFileImpl(String serverUrl, MultipartFile file, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		try {
			String fileName = file.getOriginalFilename();
			HttpPost httpPost = new HttpPost(serverUrl);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
			setUploadParams(builder, params);
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(30000).setConnectionRequestTimeout(30000)
					.setSocketTimeout(30000).build();
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpPost);// 执行提交
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				// 将响应内容转换为字符串
				result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
			}
		} catch (IOException e) {
			logger.error("出现异常",e);
		} catch (Exception e) {
			logger.error("出现异常",e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("出现异常",e);
			}
		}
		return result;
	}

	/**
	 * 设置上传文件时所附带的其他参数
	 *
	 * @param multipartEntityBuilder
	 * @param params
	 */
	private static void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,
								 Map<String, String> params) {
		if (params != null && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				multipartEntityBuilder
						.addPart(key, new StringBody(params.get(key),
								ContentType.TEXT_PLAIN));
			}
		}
	}

	private static String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}

	/**
	 * v4.3
	 * 模拟POST方式提交(表单参数)
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author wangqinghua 2014-12-19
	 */
	public static String doPost(String url, Map<String,Object> paramsMap)throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		//添加参数
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()+""));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
		String result = null;
		try {
			HttpResponse res = httpClient.execute(httpPost);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);

		} finally {
			// 关闭连接，释放资源
			httpClient.close();
		}
		return result;
	}



	public static String postJsonToRequestBody(String url, String json, Map<String, String> headers) {
		URL u = null;
		HttpURLConnection con = null;
		OutputStreamWriter osw = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		sb.append(json);
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			// // POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/json");
			//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			con.setConnectTimeout(100000);
			con.setReadTimeout(100000);
			for (String header : headers.keySet()) {
				con.setRequestProperty(header, headers.get(header));
			}

			osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			logger.error("出现异常",e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			logger.error("出现异常",e);
		}

		return buffer.toString();
	}
}
