import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/channels">
        <Translate contentKey="global.menu.entities.channels" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        <Translate contentKey="global.menu.entities.customer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cst-account">
        <Translate contentKey="global.menu.entities.cstAccount" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cst-job">
        <Translate contentKey="global.menu.entities.cstJob" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-result">
        <Translate contentKey="global.menu.entities.jobResult" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job-order">
        <Translate contentKey="global.menu.entities.jobOrder" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
