package com.mobilityguard.wizepass.wpadmingui.userdata;

public enum RdnType {

    DC(RdnTypeParserDC.class),
    OU(RdnTyperParserOu.class),
    CN(RdnTypePaserCn.class);

    private Class<RdnTypeParser> rndTypeClass;
    
    @SuppressWarnings("unchecked")
    private RdnType(final Class rndTypeClass) {
        this.rndTypeClass = rndTypeClass;
    }

    public RdnTypeParser getParser() throws InstantiationException, IllegalAccessException {
        return (RdnTypeParser) this.rndTypeClass.newInstance();
    }
}