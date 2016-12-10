package net.misykat.misykatbandung.data;

import net.misykat.misykatbandung.SoundCloudHelper;

public class Track {
    /* JSON:
    reposts_count : 2
    publisher_metadata : {u'urn': u'soundcloud:tracks:297108683', u'artist': u'My Recording'}
    visuals : None
    label_name : None
    duration : 2571080
    id : 297108683
    streamable : True
    user_id : 170413650
    title : Ust Miftah F Rakhmat: Pesan Selesai Ziarah Arbain
    commentable : True
    state : finished
    download_url : None
    comment_count : 3
    downloadable : False
    policy : ALLOW
    waveform_url : https://wis.sndcdn.com/rizWugGB8SxU_m.json
    has_downloads_left : True
    public : True
    sharing : public
    description :
    secret_token : None
    purchase_url : None
    kind : track
    purchase_title : None
    urn : soundcloud:tracks:297108683
    last_modified : 2016-12-09T20:15:41Z
    user : {u'username': u'Misykat Bandung', u'city': u'Bandung', u'first_name': u'Misykat', u'last_name': u'Bandung', u'verified': False, u'country_code': u'ID', u'avatar_url': u'https://i1.sndcdn.com/avatars-000177185241-fouxka-large.jpg', u'urn': u'soundcloud:users:170413650', u'kind': u'user', u'uri': u'https://api.soundcloud.com/users/170413650', u'permalink': u'misykat-bandung', u'last_modified': u'2016-02-17T16:04:04Z', u'full_name': u'Misykat Bandung', u'permalink_url': u'https://soundcloud.com/misykat-bandung', u'id': 170413650}
    genre : Religion & Spirituality
    download_count : 0
    full_duration : 2571080
    permalink_url : https://soundcloud.com/misykat-bandung/ust-miftah-f-rakhmat-pesan-selesai-ziarah-arbain
    likes_count : 5
    permalink : ust-miftah-f-rakhmat-pesan-selesai-ziarah-arbain
    playback_count : 130
    license : all-rights-reserved
    monetization_model : NOT_APPLICABLE
    artwork_url : https://i1.sndcdn.com/artworks-000197690029-cthu0k-large.jpg
    created_at : 2016-12-09T20:15:30Z
    uri : https://api.soundcloud.com/tracks/297108683
    release_date : None
    tag_list : Blues Pop
    embeddable_by : all
     */

    String id;
    String fullTitle;
    String speakerName;
    String title;
    String waveformUrl;
    String urn;
    String lastModified;
    String genre;
    String permalinkUrl;
    String uri;
    String artworkUrl;
    Integer fullDuration;

    public Track(String id, String fullTitle, String waveformUrl, String urn, String lastModified, String genre, String permalinkUrl, String uri, String artworkUrl, Integer fullDuration) {
        this.id = id;
        this.fullTitle = fullTitle;
        this.waveformUrl = waveformUrl;
        this.urn = urn;
        this.lastModified = lastModified;
        this.genre = genre;
        this.permalinkUrl = permalinkUrl;
        this.uri = uri;
        this.artworkUrl = artworkUrl;
        this.fullDuration = fullDuration;

        if (fullTitle.contains(":")) {
            String[] titles = fullTitle.split(":");
            speakerName = titles[0].trim();
            title = titles[1].trim();
        } else {
            title = fullTitle;
        }
    }

    public String getId() {
        return id;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public String getTitle() {
        return title;
    }

    public String getWaveformUrl() {
        return waveformUrl;
    }

    public String getUrn() {
        return urn;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getGenre() {
        return genre;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public String getUri() {
        return uri;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public Integer getFullDuration() {
        return fullDuration;
    }

    public String getStreamUrl() {
        return uri + "&clientId" + SoundCloudHelper.CLIENT_ID;
    }
}
