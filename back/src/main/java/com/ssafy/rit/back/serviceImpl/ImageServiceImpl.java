package com.ssafy.rit.back.serviceImpl;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.rit.back.config.S3Config;
import com.ssafy.rit.back.entity.Member;
import com.ssafy.rit.back.exception.member.MemberNotFoundException;
import com.ssafy.rit.back.repository.MemberRepository;
import com.ssafy.rit.back.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private S3Config s3Config;
    private MemberRepository memberRepository;

    @Autowired
    public ImageServiceImpl(S3Config s3Config, MemberRepository memberRepository) {
        this.s3Config = s3Config;
        this.memberRepository = memberRepository;
    }

    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucket;

    // 굳이 해야 하나?
    private String localLocation = System.getProperty("java.io.tmpdir");


    @Override
    @Transactional
    public String imageUpload(MultipartRequest request, Member member) throws IOException {
        MultipartFile file = request.getFile("upload");

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        File localFile = new File(localPath);
        file.transferTo(localFile);

        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        // DB에서 Member profileImage 필드 갱신
        member.updateProfile(s3Url);

        localFile.delete();

        return s3Url;

    }


    public Member getMemberForProfileUpload(String nickname) {
        return memberRepository.findByNickname(nickname).orElseThrow(MemberNotFoundException::new);
    }


}
