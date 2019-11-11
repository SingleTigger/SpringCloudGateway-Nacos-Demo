package com.chenws.gateway.filter.facotry;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Created by chenws on 2019/11/5.
 * 修改带有body的POST请求，可添加或检验参数。
 * 局部过滤器，以后可考虑改为全局
 */
@Slf4j
@Component
public class CModifyRequestBodyGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            //利用ServerHttpRequestDecorator来解析request
            ServerHttpRequestDecorator serverHttpRequestDecorator = new ServerHttpRequestDecorator(request) {

                /**
                 * 如果
                 * @return
                 */
                @Override
                public URI getURI() {
                    return super.getURI();
                }

                @Override
                public Flux<DataBuffer> getBody() {
                    Flux<DataBuffer> body = super.getBody();
                    return body.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        DataBufferUtils.release(dataBuffer);
                        //得到request body的json格式数据（默认应当传json）
                        String bodyJson = new String(content, StandardCharsets.UTF_8);
                        log.info("bodyJson  {}", bodyJson);
                        //此处可对body进行修改。
                        byte[] bytes = bodyJson.getBytes();
                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
                        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
                        buffer.write(bytes);
                        return buffer;
                    });
                }

                /**
                 * 复写getHeaders方法，如果为POST请求则删除content-length，添加Transfer-Encoding
                 * @return HttpHeaders
                 */
                @Override
                public HttpHeaders getHeaders() {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.putAll(super.getHeaders());
                    String methodValue = this.getMethodValue();
                    if (HttpMethod.POST.name().equals(methodValue)) {
                        //由于修改了请求体的body，导致content-length长度不确定，因此使用分块编码
                        httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                    }
                    return httpHeaders;
                }


            };
            return chain.filter(exchange.mutate().request(serverHttpRequestDecorator).build()).then(Mono.fromRunnable(()->{
            }));
        };
    }
}
