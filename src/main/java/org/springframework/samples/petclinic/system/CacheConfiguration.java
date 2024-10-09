package org.springframework.samples.petclinic.system;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
// キャッシュ機能を有効にする
@EnableCaching
public class CacheConfiguration {
	
	@Bean
	// JCacheManagerCustomizer 解説しているサイトが英語ばかりで内容がよくわからない
	// キャッシュの追加をする際に使用するインターフェースのように見える
	public JCacheManagerCustomizer petclinicCacheConfigurationCostomizer() {
		// vetsをキャッシュ化している？
		return cm -> cm.createCache("vets", cacheConfiguration());
	}
	private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration(){
		return new MutableConfiguration<>().setStatisticsEnabled(true);
	}

}
