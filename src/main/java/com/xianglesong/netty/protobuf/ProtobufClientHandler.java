package com.xianglesong.netty.protobuf;

import com.google.protobuf.MessageLite;

import com.example.tutorial.AddressBookProtos;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by rollin on 18/3/20.
 */
public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //当channel就绪后，我们首先通过client发送一个数据。
        ctx.writeAndFlush(build());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AddressBookProtos.Person person = (AddressBookProtos.Person)msg;
        System.out.println(person.getEmail());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();;
        ctx.close();
    }

    public MessageLite build() {
        AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
        personBuilder.setEmail("test@xianglesong.com");
        personBuilder.setId(1000);
        AddressBookProtos.Person.PhoneNumber.Builder phone = AddressBookProtos.Person.PhoneNumber.newBuilder();
        phone.setNumber("18610000000");

        personBuilder.setName("测试");
        personBuilder.addPhones(phone);

        return personBuilder.build();
    }

}