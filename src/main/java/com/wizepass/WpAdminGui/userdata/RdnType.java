package com.wizepass.WpAdminGui.userdata;

public enum RdnType {
	ROOT(RdnTypeParserRoot.class),
    DC(RdnTypeParserDC.class),
    OU(RdnTyperParserOu.class),
    CN(RdnTypeParserCn.class);

    private Class<RdnTypeParser> rndTypeClass;
    
    @SuppressWarnings("unchecked")
    private RdnType(final Class rndTypeClass) {
        this.rndTypeClass = rndTypeClass;
    }

    public RdnTypeParser getParser() throws InstantiationException, IllegalAccessException {
        return (RdnTypeParser) this.rndTypeClass.newInstance();
    }
}