package com.xianglesong.netty.protobuf;

import com.google.protobuf.MessageLite;

import com.example.tutorial.AddressBookProtos;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rollin on 18/3/20.
 */
public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AddressBookProtos.Person person = (AddressBookProtos.Person) msg;
        //经过pipeline的各个decoder，到此Person类型已经可以断定
        System.out.println(person.getEmail());
        ChannelFuture future = ctx.writeAndFlush(build());
        //发送数据之后，我们手动关闭channel，这个关闭是异步的，当数据发送完毕后执行。
        future.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 构建一个Protobuf实例，测试
     */
    public MessageLite build() {
        AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
        personBuilder.setEmail("hr@xianglesong.com");
        personBuilder.setId(1000);
        AddressBookProtos.Person.PhoneNumber.Builder phone = AddressBookProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18610000000");

        personBuilder.setName("人力资源");
        personBuilder.addPhones(phone);

        return personBuilder.build();
    }
}
