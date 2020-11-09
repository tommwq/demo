package com.tommwq;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.apache.maven.model.*;
import org.apache.maven.project.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import java.nio.*;
import java.nio.channels.*;

public class BuildInfoGenerator {
    public static BuildInfo generateBuildInfo(MavenProject project, Git git) throws IOException, GitAPIException {
        Repository repository = git.getRepository();
        String branch = repository.getBranch();
        String origHead = ObjectId.toString(repository.readOrigHead());
        boolean isClean = git.status().call().isClean();
        String head = "";

        for (Ref ref: repository.getRefDatabase().getRefsByPrefix("HEAD")) {
            head = ObjectId.toString(ref.getObjectId());
        }

        BuildInfo info = new BuildInfo();
        info.origHead = origHead;
        info.head = head;
        info.branch = branch;
        info.isClean = isClean;

        return info;
    }

    public static String generateJavaSource(BuildInfo buildInfo) throws IOException {
        Map<String, String> values = new HashMap<>();
        values.put("HEAD", buildInfo.head);
        values.put("ORIG_HEAD", buildInfo.origHead);
        values.put("BRANCH", buildInfo.branch);
        values.put("HAVE_UNCOMMITTED_CHANGES", buildInfo.isClean ? "false" : "true");
        
        try (InputStream stream = BuildInfoGenerator.class.getResourceAsStream("/BuildInfo.java")) {
            return render(stream, values);
        }
    }

    public static void writeBuildInfo(File sourceDirectory, BuildInfo buildInfo)
        throws IOException, FileNotFoundException, UnsupportedEncodingException {
        String javaFileName = "BuildInfo.java";
        File javaFile = sourceDirectory.toPath()
            .resolve("build")
            .resolve(javaFileName)
            .toFile();        
        if (!javaFile.exists()) {
            javaFile.getParentFile().mkdirs();
            javaFile.createNewFile();
        }

        try (FileChannel channel = new FileOutputStream(javaFile, true).getChannel()) {
            channel.truncate(0);
            channel.write(ByteBuffer.wrap(generateJavaSource(buildInfo).getBytes("utf-8")));
        }
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, length);
        }
 
        return byteStream.toByteArray();
    }

    public static String render(InputStream templateFile, Map<String, String> values) throws IOException {
        String templateString = new String(readAll(templateFile));
        for (Map.Entry<String, String> entry: values.entrySet()) {
            templateString = templateString.replace("(" + entry.getKey() + ")", entry.getValue());
        }
        return templateString;
    }
    
    /**
     * 从subDirectory开始向上搜索git仓库目录。
     * @param dir 搜索起点
     * @return 返回找到的git仓库目录或null。
     */
    public static File searchGitDirectory(File dir) {
        while (dir != null && !isGitRepository(dir)) {
            dir = dir.getParentFile();
        }
        return dir;
    }

    /**
     * 判断目录是否是git仓库目录。
     * @param directory 要检查的目录
     * @return 如果directory包含.git子目录，则认为是git仓库目录，返回true。
     */
    public static boolean isGitRepository(File directory) {
        if (!directory.isDirectory()) {
            return false;
        }

        return directory.listFiles((File file) -> {
                return file.isDirectory() && file.getName().equals(".git");
            }).length > 0;
    }

    public static void generate(MavenProject project) throws IOException,
        GitAPIException,
        IOException,
        FileNotFoundException,
        UnsupportedEncodingException {

        File sourceDirectory = new File(project.getBuild().getSourceDirectory());
        try (Git git = Git.open(searchGitDirectory(sourceDirectory))) {
            writeBuildInfo(sourceDirectory, generateBuildInfo(project, git));
        }
    }
}
