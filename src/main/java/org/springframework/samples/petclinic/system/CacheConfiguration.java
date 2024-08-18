package org.springframework.samples.petclinic.system;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JCache API を提供するキャッシュを対象としたキャッシュ構成。この構成
 * アプリケーションの使用済みキャッシュを作成し、統計情報を有効にします。
 * JMX 経由でアクセス可能。
 */
@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfiguration {
	
	@Bean
	public JCacheManagerCustomizer petclinicCacheConfigurationCustmizer() {
		return cm -> cm.createCache("vers", cacheConfiguration());
	}
	/**
	 * JCache プログラムによる統計を有効にする簡単な構成を作成します。
	 * 設定 API。
	 *<p>
	 * JCache API 標準によって提供される構成オブジェクト内には、
	 * 非常に限定された構成オプションのセットにすぎません。本当に関連性のあるもの
	 * 構成オプション (サイズ制限など) は構成を通じて設定する必要があります
	 * 選択した JCache 実装によって提供されるメカニズム。
	 */
	private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
		return new MutableConfiguration<>().setStatisticsEnabled(true);
	}
}
