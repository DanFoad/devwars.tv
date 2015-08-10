package com.bezman.controller;

import com.bezman.Reference.Reference;
import com.bezman.annotation.PreAuthorization;
import com.bezman.annotation.UnitOfWork;
import com.bezman.model.Team;
import com.bezman.model.User;
import com.bezman.service.GameService;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.hibernate.internal.SessionImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Ref;
import java.util.zip.GZIPInputStream;

/**
 * Created by Terence on 8/5/2015.
 */
@Controller
@RequestMapping("/v1/team")
public class TeamController extends BaseController
{

    /**
     * Upload for the teams data straight from cloud nine
     * @param session
     * @param id ID of team to set
     * @param zipFile tar.gz file from Cloud Nine
     * @return
     * @throws IOException
     */
    @PreAuthorization(minRole = User.Role.ADMIN)
    @UnitOfWork
    @RequestMapping(value = "/{id}/upload", method = RequestMethod.POST)
    public ResponseEntity uploadSite(SessionImpl session, @PathVariable("id") int id, @RequestPart("zip") MultipartFile zipFile) throws IOException
    {
        Team team = (Team) session.get(Team.class, id);

        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GZIPInputStream(zipFile.getInputStream()));

        String destPath = Reference.SITE_STORAGE_PATH + File.separator + team.getGame().getId() + File.separator + team.getName();

        TarArchiveEntry tarArchiveEntry = null;
        while((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null)
        {
            String fileName = tarArchiveEntry.getName().replaceAll("[^a-zA-Z0-9.-]", "_");

            fileName = fileName.split("_")[fileName.split("_").length -1];

            File file = new File(destPath, fileName);

            if (tarArchiveEntry.isDirectory())
            {
                file.mkdirs();
            } else
            {
                file.createNewFile();

                IOUtils.copy(tarArchiveInputStream, new FileOutputStream(file));
            }

        }

        return new ResponseEntity("Successfully Uploaded Team's files", HttpStatus.OK);
    }


}