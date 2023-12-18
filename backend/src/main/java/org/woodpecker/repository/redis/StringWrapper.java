package org.woodpecker.repository.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Builder
@ToString
@Getter
@Setter
@RedisHash("stringWrapper")
public class StringWrapper {
    @Id
    private String id;
    @Indexed
    private String name;
}
